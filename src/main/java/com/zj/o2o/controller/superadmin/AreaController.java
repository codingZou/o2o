package com.zj.o2o.controller.superadmin;

import com.zj.o2o.entity.Area;
import com.zj.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-01-17 18:36
 */
@Controller
@RequestMapping(value = "superAdmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "listArea", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> listArea() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Area> areaList = areaService.listArea();
            map.put("rows", areaList);
            map.put("total", areaList.size());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");
        return map;
    }
}
