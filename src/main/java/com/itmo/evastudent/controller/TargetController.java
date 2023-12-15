package com.itmo.evastudent.controller;

import com.itmo.evastudent.common.BaseResponse;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.common.ResultUtils;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.model.vo.SecondTargetVO;
import com.itmo.evastudent.service.TargetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/12/15 14:15
 */
@RestController
@RequestMapping("/target")
public class TargetController {

    @Resource
    private TargetService targetService;

    /**
     * 获取二级指标列表
     * @param targetId 一级指标id
     * @return 二级指标列表
     */
    @GetMapping("/get/list")
    public BaseResponse<List<SecondTargetVO>> getSecondTargetList(Long targetId) {
        if (targetId == null || targetId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "一级指标为空");
        }

        List<SecondTargetVO> secondTargetVOList = targetService.listSecondTarget(targetId);

        return ResultUtils.success(secondTargetVOList);
    }
}
