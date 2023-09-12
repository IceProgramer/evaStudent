package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.model.entity.Evaluation;
import com.itmo.evastudent.service.EvaluationService;
import com.itmo.evastudent.mapper.EvaluationMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【evaluation(评价指标表)】的数据库操作Service实现
* @createDate 2023-09-12 15:53:18
*/
@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation>
    implements EvaluationService {

}




