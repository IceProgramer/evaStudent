package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.itmo.evastudent.constant.EvaluationContent;
import com.itmo.evastudent.constant.TargetLevelContent;
import com.itmo.evastudent.manager.UserManager;
import com.itmo.evastudent.mapper.*;
import com.itmo.evastudent.model.entity.*;
import com.itmo.evastudent.model.vo.CourseVO;
import com.itmo.evastudent.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjiahan
 * @description 针对表【course(课程表)】的数据库操作Service实现
 * @createDate 2023-09-12 19:29:44
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
        implements CourseService {

    private final static Gson GSON = new Gson();

    @Resource
    private TargetScoreMapper scoreMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private TargetMapper targetMapper;

    @Resource
    private UserManager userManager;

    @Override
    public void insertCourseTarget(Long userId, HttpServletRequest request) {
        Student student = userManager.getLoginStudent(request);
        Evaluation evaluation = EvaluationContent.get();

        TargetScore oldTargetScore = scoreMapper.selectOne(Wrappers.<TargetScore>lambdaQuery()
                .eq(TargetScore::getStudentId, userId)
                .eq(TargetScore::getEvaluationId, evaluation.getId())
                .select(TargetScore::getId)
                .last("limit 1"));
        if (oldTargetScore != null) {
            return;
        }

        List<Target> targetList = targetMapper.selectList(Wrappers.<Target>lambdaQuery()
                .eq(Target::getTargetLevel, TargetLevelContent.FIRST_LEVEL)
                .select(Target::getId, Target::getNationality));

        // 评价指标map => nationality -> id
        Map<Integer, List<Target>> targetMap = targetList.stream()
                .collect(Collectors.groupingBy(Target::getNationality));

        // 获取课程列表
        List<Course> courseList = baseMapper.selectList(Wrappers.<Course>lambdaQuery()
                .eq(Course::getGrade, student.getGrade())
                .eq(Course::getMajor, student.getMajor())
                .select(Course::getId, Course::getTeacherIds));

        Set<Long> teacherIdSet = new HashSet<>();
        for (Course course : courseList) {
            String teacherIdStr = course.getTeacherIds();
            Set<Long> teacherIds = GSON.fromJson(teacherIdStr, new TypeToken<Set<Long>>() {
            }.getType());
            teacherIdSet.addAll(teacherIds);
        }
        List<Teacher> teacherList = teacherMapper.selectList(Wrappers.<Teacher>lambdaQuery()
                .in(Teacher::getId, teacherIdSet)
                .select(Teacher::getId, Teacher::getNationality));
        // 教师map id -> nationality
        Map<Long, Integer> teacherMap = teacherList.stream()
                .collect(Collectors.toMap(Teacher::getId, Teacher::getNationality));

        // 插入数据
        List<TargetScore> insertTargetScore = new ArrayList<>();
        for (Course course : courseList) {
            String teacherIdStr = course.getTeacherIds();
            Set<Long> teacherIds = GSON.fromJson(teacherIdStr, new TypeToken<Set<Long>>() {
            }.getType());

            // 根据教师插入
            for (Long teacherId : teacherIds) {
                Integer nationality = teacherMap.get(teacherId);
                List<Target> firstTargetList = targetMap.get(nationality);
                for (Target target : firstTargetList) {
                    TargetScore targetScore = new TargetScore();
                    targetScore.setStudentId(userId);
                    targetScore.setTeacherId(teacherId);
                    targetScore.setCourseId(course.getId());
                    targetScore.setTargetId(target.getId());
                    targetScore.setEvaluationId(evaluation.getId());
                    insertTargetScore.add(targetScore);
                }
            }
        }

        if (!targetList.isEmpty()) {
            scoreMapper.insertBatch(insertTargetScore);
        }
    }

    @Override
    public List<CourseVO> listDoneCourse(HttpServletRequest request) {
        Student student = userManager.getLoginStudent(request);
        Evaluation evaluation = EvaluationContent.get();

        List<TargetScore> targetScoreList = scoreMapper.selectList(Wrappers.<TargetScore>lambdaQuery()
                .eq(TargetScore::getStudentId, student.getId())
                .ne(TargetScore::getScore, 0.00)
                .select(TargetScore::getCourseId));
        // 获取课程id列表
        List<Long> courseIdList = targetScoreList.stream()
                .map(TargetScore::getCourseId)
                .collect(Collectors.toList());

        List<Course> courseList = baseMapper.selectList(Wrappers.<Course>lambdaQuery()
                .in(Course::getId, courseIdList)
                .select(Course::getCourseChineseName, Course::getCourseEnglishName, Course::getId));

        return courseList.stream().map(course -> {
            CourseVO courseVO = new CourseVO();
            BeanUtils.copyProperties(course, courseVO);

            return courseVO;
        }).collect(Collectors.toList());
    }

}



