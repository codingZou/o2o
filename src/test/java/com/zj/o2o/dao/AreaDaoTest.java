package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author zj
 * @create 2019-01-17 17:48
 */
public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void queryAreaList() {
        List<Area> areList = areaDao.listArea();
        assertEquals(3, areList.size());
    }
}