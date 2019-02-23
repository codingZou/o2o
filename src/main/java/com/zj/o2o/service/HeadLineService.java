package com.zj.o2o.service;

import com.zj.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author zj
 * @create 2019-02-19 16:05
 */
public interface HeadLineService {
    /**
     * 根据查询条件返回头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> listHeadLine(HeadLine headLineCondition) throws IOException;
}
