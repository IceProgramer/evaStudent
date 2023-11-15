package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.model.entity.Target;
import com.itmo.evastudent.service.TargetService;
import com.itmo.evastudent.mapper.TargetMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【target(评价指标表)】的数据库操作Service实现
* @createDate 2023-09-12 19:18:28
*/
@Service
public class TargetServiceImpl extends ServiceImpl<TargetMapper, Target>
    implements TargetService {

}




