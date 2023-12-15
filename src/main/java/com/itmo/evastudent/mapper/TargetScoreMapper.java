package com.itmo.evastudent.mapper;

import com.itmo.evastudent.model.entity.TargetScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【targetScore(一级指标分数表)】的数据库操作Mapper
* @createDate 2023-09-12 18:54:52
* @Entity com.itmo.evastudent.model.entity.TargetScore
*/
public interface TargetScoreMapper extends BaseMapper<TargetScore> {

    /**
     * 批量插入指标信息
     * @param targetScoreList 指标信息
     */
    void insertBatch(@Param("targetScoreList") List<TargetScore> targetScoreList);

    @Select("SELECT courseId FROM targetScore WHERE studentId = #{studentId} AND evaluationId = #{evaluationId} GROUP BY courseId HAVING MIN(score) > 0")
    List<TargetScore> selectDoneCourseIdList(@Param("studentId") Long studentId, @Param("evaluationId") Long evaluationId);
}




