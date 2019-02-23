package com.zj.o2o.service.impl;

import com.zj.o2o.dao.HeadLineDao;
import com.zj.o2o.entity.HeadLine;
import com.zj.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author zj
 * @create 2019-02-19 16:09
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> listHeadLine(HeadLine headLineCondition) throws IOException {
        return headLineDao.listHeadLine(headLineCondition);
    }
}
