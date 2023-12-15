package com.itmo.evastudent.service;

import com.itmo.evastudent.model.entity.Target;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.evastudent.model.vo.SecondTargetVO;

import java.util.List;

/**
 * @author chenjiahan
 * @description 针对表【target(评价指标表)】的数据库操作Service
 * @createDate 2023-09-12 19:18:28
 */
public interface TargetService extends IService<Target> {

    /**
     * 获取二级指标列表
     *
     * @param targetId 一级指标id
     * @return 二级指标列表
     */
    List<SecondTargetVO> listSecondTarget(Long targetId);
}
