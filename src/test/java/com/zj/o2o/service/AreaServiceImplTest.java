package com.zj.o2o.service;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author zj
 * @create 2019-01-17 18:17
 */
public class AreaServiceImplTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() {
        List<Area> areaList = areaService.listArea();
        assertEquals("龙华新区", areaList.get(0).getAreaName());
    }
}