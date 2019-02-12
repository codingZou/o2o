package com.zj.o2o.service.impl;

import com.zj.o2o.dao.AreaDao;
import com.zj.o2o.entity.Area;
import com.zj.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zj
 * @create 2019-01-17 18:13
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> listArea() {
        List<Area> list = areaDao.listArea();
        return list;
    }
}
