package com.itmo.evastudent.service;

import com.itmo.evastudent.model.dto.score.ScorePointRequest;
import com.itmo.evastudent.model.entity.TargetScore;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjiahan
 * @description 针对表【targetScore(一级指标分数表)】的数据库操作Service
 * @createDate 2023-09-12 18:54:52
 */
public interface TargetScoreService extends IService<TargetScore> {

    /**
     * 学生给分
     *
     * @param scorePointRequest 一级指标信息
     * @param request           网络请求
     * @return 给分成功
     */
    Boolean pointScore(ScorePointRequest scorePointRequest, HttpServletRequest request);
}
