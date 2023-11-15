package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.model.entity.TargetScore;
import com.itmo.evastudent.service.TargetScoreService;
import com.itmo.evastudent.mapper.TargetScoreMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【targetScore(一级指标分数表)】的数据库操作Service实现
* @createDate 2023-09-12 18:54:52
*/
@Service
public class TargetScoreServiceImpl extends ServiceImpl<TargetScoreMapper, TargetScore>
    implements TargetScoreService {

}




