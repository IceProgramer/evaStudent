package com.itmo.evastudent.manager;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.mapper.EvaluationMapper;
import com.itmo.evastudent.model.entity.Evaluation;
import com.itmo.evastudent.model.enums.EvaluationStatusEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/11/17 12:28
 */
@Component
public class EvaluationManager {

    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * 获取当前正在进行中的评测信息
     *
     * @return 评测信息
     */
    public Evaluation getCurrentEvaluation() {
        Evaluation evaluation = evaluationMapper.selectOne(Wrappers.<Evaluation>lambdaQuery()
                .eq(Evaluation::getEvaluationStatus, EvaluationStatusEnum.OPENING.getValue())
                .select(Evaluation::getId)
                .last("limit 1"));

        if (evaluation == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "没有评测正在进行中");
        }

        return evaluation;
    }

}
