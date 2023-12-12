package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.constant.TargetLevelContent;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.manager.EvaluationManager;
import com.itmo.evastudent.manager.UserManager;
import com.itmo.evastudent.mapper.*;
import com.itmo.evastudent.model.entity.*;
import com.itmo.evastudent.model.enums.TargetStatusEnum;
import com.itmo.evastudent.model.vo.CourseInfoVO;
import com.itmo.evastudent.model.vo.CourseVO;
import com.itmo.evastudent.model.vo.FirstTargetVO;
import com.itmo.evastudent.model.vo.TeacherVO;
import com.itmo.evastudent.service.CourseService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    @Resource
    private EvaluationManager evaluationManager;

    @Override
    public void insertCourseTarget(Long userId, HttpServletRequest request) {
        Student student = userManager.getLoginStudent(request);

        // 获取当前正在进行中的评测信息
        Evaluation evaluation = evaluationManager.getCurrentEvaluation();

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
    public List<CourseVO> listUndoneCourse(HttpServletRequest request) {
        Student student = userManager.getLoginStudent(request);
        Evaluation currentEvaluation = evaluationManager.getCurrentEvaluation();

        List<TargetScore> targetScoreList = scoreMapper.selectList(Wrappers.<TargetScore>lambdaQuery()
                .eq(TargetScore::getStudentId, student.getId())
                .eq(TargetScore::getScore, 0.00)
                .eq(TargetScore::getEvaluationId, currentEvaluation.getId())
                .select(TargetScore::getCourseId, TargetScore::getTargetId));

        return getCourseVOList(targetScoreList);
    }

    @Override
    public List<CourseVO> listDoneCourse(HttpServletRequest request) {
        Student student = userManager.getLoginStudent(request);
        Evaluation currentEvaluation = evaluationManager.getCurrentEvaluation();


        List<TargetScore> targetScoreList = scoreMapper.selectList(Wrappers.<TargetScore>lambdaQuery()
                .eq(TargetScore::getStudentId, student.getId())
                .ne(TargetScore::getScore, 0.00)
                .eq(TargetScore::getEvaluationId, currentEvaluation.getId())
                .select(TargetScore::getCourseId));

        return getCourseVOList(targetScoreList);
    }

    /**
     * 获取课程列表
     *
     * @param targetScoreList 答题情况
     * @return CourseVO 列表
     */
    private List<CourseVO> getCourseVOList(List<TargetScore> targetScoreList) {

        // 获取课程id列表
        List<Long> courseIdList = targetScoreList.stream()
                .map(TargetScore::getCourseId)
                .collect(Collectors.toList());

        if (courseIdList.size() <= 0) {
            return null;
        }

        // 获取课程信息
        List<Course> courseList = baseMapper.selectList(Wrappers.<Course>lambdaQuery()
                .in(Course::getId, courseIdList)
                .select(Course::getCourseChineseName, Course::getCourseEnglishName, Course::getId));

        return courseList.stream().map(course -> {
            CourseVO courseVO = new CourseVO();
            BeanUtils.copyProperties(course, courseVO);

            return courseVO;
        }).collect(Collectors.toList());
    }

    @Override
    public CourseInfoVO getCourseById(Long courseId) {

        Course course = baseMapper.selectOne(Wrappers.<Course>lambdaQuery()
                .eq(Course::getId, courseId)
                .last("limit 1"));

        if (course == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "课程信息不存在");
        }

        return getCourseVO(course);
    }

    /**
     * 获取 CourseVO
     *
     * @param course 课程信息
     * @return CourseVO
     */
    private CourseInfoVO getCourseVO(Course course) {
        Evaluation currentEvaluation = evaluationManager.getCurrentEvaluation();
        Long evaluationId = currentEvaluation.getId();
        CourseInfoVO courseInfoVO = new CourseInfoVO();

        // 课程基本信息
        BeanUtils.copyProperties(course, courseInfoVO);

        // 获取所有评分信息
        List<TargetScore> targetScoreList = scoreMapper.selectList(Wrappers.<TargetScore>lambdaQuery()
                .eq(TargetScore::getEvaluationId, evaluationId)
                .eq(TargetScore::getCourseId, courseInfoVO.getId())
                .select(TargetScore::getTargetId, TargetScore::getTeacherId, TargetScore::getScore, TargetScore::getId));

        Map<Long, List<TargetScore>> teacherScoreMap = targetScoreList.stream()
                .collect(Collectors.groupingBy(TargetScore::getTeacherId));

        Set<Long> teacherIds = teacherScoreMap.keySet();

        // 获取所有教师名称
        List<Teacher> teacherList = teacherMapper.selectList(Wrappers.<Teacher>lambdaQuery()
                .in(Teacher::getId, teacherIds)
                .select(Teacher::getId, Teacher::getTeacherName));

        // teacherId -> teacherName
        Map<Long, String> teacherMap = teacherList.stream()
                .collect(Collectors.toMap(Teacher::getId, Teacher::getTeacherName));

        // 获取所有一级指标信息
        List<Target> targetList = targetMapper.selectList(Wrappers.<Target>lambdaQuery()
                .eq(Target::getTargetLevel, TargetLevelContent.FIRST_LEVEL)
                .select(Target::getId, Target::getTargetChineseName, Target::getTargetEnglishName));

        // targetId -> TargetChineseName, TargetEnglishName
        Map<Long, Pair<String, String>> targetMap = targetList.stream()
                .collect(Collectors.toMap(Target::getId,
                        target -> Pair.of(target.getTargetChineseName(), target.getTargetEnglishName())));
        List<TeacherVO> teacherVOList = new ArrayList<>();
        // 遍历每一位教师的信息
        for (Map.Entry<Long, List<TargetScore>> entry : teacherScoreMap.entrySet()) {
            TeacherVO teacherVO = new TeacherVO();
            Long teacherId = entry.getKey();
            List<TargetScore> scoreList = entry.getValue();

            teacherVO.setTeacherName(teacherMap.get(teacherId));

            // 获取一级指标信息
            List<FirstTargetVO> targetVOList = scoreList.stream().map(targetScore -> {
                Long targetId = targetScore.getTargetId();
                Pair<String, String> targetNames = targetMap.get(targetId);
                FirstTargetVO firstTargetVO = new FirstTargetVO();

                firstTargetVO.setId(targetId);
                firstTargetVO.setTargetChineseName(targetNames.getLeft());
                firstTargetVO.setTargetEnglishName(targetNames.getRight());
                int targetStatus = targetScore.getScore().compareTo(BigDecimal.ZERO) == 0 ?
                        TargetStatusEnum.UNDONE.getValue() :
                        TargetStatusEnum.DONE.getValue();
                firstTargetVO.setStatus(targetStatus);
                return firstTargetVO;
            }).collect(Collectors.toList());
            teacherVO.setFirstTargetVOList(targetVOList);

            teacherVOList.add(teacherVO);
        }

        courseInfoVO.setTeacherList(teacherVOList);
        return courseInfoVO;
    }
}



