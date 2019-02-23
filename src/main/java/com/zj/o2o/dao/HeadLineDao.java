package com.zj.o2o.dao;

import com.zj.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-19 15:20
 */
public interface HeadLineDao {
    /**
     * 根据查询条件查询广告头条名
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> listHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
