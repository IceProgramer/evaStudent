package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.model.entity.Target;
import com.itmo.evastudent.model.vo.SecondTargetVO;
import com.itmo.evastudent.service.TargetService;
import com.itmo.evastudent.mapper.TargetMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjiahan
 * @description 针对表【target(评价指标表)】的数据库操作Service实现
 * @createDate 2023-09-12 19:18:28
 */
@Service
public class TargetServiceImpl extends ServiceImpl<TargetMapper, Target>
        implements TargetService {

    @Override
    public List<SecondTargetVO> listSecondTarget(Long targetId) {
        List<Target> secondTargetList = baseMapper.selectList(Wrappers.<Target>lambdaQuery()
                .eq(Target::getTargetId, targetId)
                .select(Target::getTargetId, Target::getTargetChineseName,
                        Target::getTargetEnglishName));

        List<SecondTargetVO> secondTargetVOList = secondTargetList.stream().map(target -> {
            SecondTargetVO secondTargetVO = new SecondTargetVO();
            BeanUtils.copyProperties(target, secondTargetVO);
            secondTargetVO.setId(targetId);
            return secondTargetVO;
        }).collect(Collectors.toList());

        return secondTargetVOList;
    }
}




