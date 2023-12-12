package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.manager.UserManager;
import com.itmo.evastudent.model.dto.score.ScorePointRequest;
import com.itmo.evastudent.model.entity.Student;
import com.itmo.evastudent.model.entity.TargetScore;
import com.itmo.evastudent.service.TargetScoreService;
import com.itmo.evastudent.mapper.TargetScoreMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author chenjiahan
 * @description 针对表【targetScore(一级指标分数表)】的数据库操作Service实现
 * @createDate 2023-09-12 18:54:52
 */
@Service
public class TargetScoreServiceImpl extends ServiceImpl<TargetScoreMapper, TargetScore>
        implements TargetScoreService {

    @Resource
    private UserManager userManager;

    @Override
    public Boolean pointScore(ScorePointRequest pointRequest, HttpServletRequest request) {
        Student loginStudent = userManager.getLoginStudent(request);

        // 校验分数信息
        validScorePoint(pointRequest);

        TargetScore targetScore = baseMapper.selectOne(Wrappers.<TargetScore>lambdaQuery()
                .eq(TargetScore::getTargetId, pointRequest.getTargetId())
                .eq(TargetScore::getCourseId, pointRequest.getCourseId())
                .eq(TargetScore::getTeacherId, pointRequest.getTeacherId())
                .eq(TargetScore::getStudentId, loginStudent.getId())
                .select(TargetScore::getId, TargetScore::getScore));

        if (targetScore == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "评分失败");
        }

        targetScore.setScore(BigDecimal.valueOf(pointRequest.getScore()));

        // 更新分数信息
        baseMapper.updateById(targetScore);

        return true;
    }

    /**
     * 校验分数信息
     *
     * @param scorePointRequest 分数信息
     */
    private void validScorePoint(ScorePointRequest scorePointRequest) {
        Long courseId = scorePointRequest.getCourseId();
        Long teacherId = scorePointRequest.getTeacherId();
        Long targetId = scorePointRequest.getTargetId();
        Double score = scorePointRequest.getScore();

        if (courseId == null || courseId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程id错误");
        }

        if (teacherId == null || teacherId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教师id错误");
        }

        if (targetId == null || targetId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "一级指标id错误");
        }

        if (score == null || score <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分数错误");
        }
    }
}




