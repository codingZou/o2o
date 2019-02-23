package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author zj
 * @create 2019-02-19 15:42
 */
public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void listHeadLine() {
        List<HeadLine> headLines = headLineDao.listHeadLine(new HeadLine());
        assertEquals(4, headLines.size());
    }
}