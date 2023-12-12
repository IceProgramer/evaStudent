package com.itmo.evastudent.controller;

import com.itmo.evastudent.common.BaseResponse;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.common.ResultUtils;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.model.dto.score.ScorePointRequest;
import com.itmo.evastudent.service.TargetScoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/12/12 12:56
 */
@RestController
@RequestMapping("/target")
public class ScoreController {

    @Resource
    private TargetScoreService scoreService;

    @PostMapping("/score/point")
    public BaseResponse<Boolean> pointScore(@RequestBody ScorePointRequest scorePointRequest, HttpServletRequest request) {
        if (scorePointRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评分请求为空");
        }

        boolean result = scoreService.pointScore(scorePointRequest, request);

        return ResultUtils.success(result);
    }

}
