package com.zj.o2o.controller.frontend;

import com.zj.o2o.entity.HeadLine;
import com.zj.o2o.entity.PersonInfo;
import com.zj.o2o.entity.ShopCategory;
import com.zj.o2o.service.HeadLineService;
import com.zj.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-02-19 16:13
 */
@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    /**
     * 前端首页信息数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
    public Map<String, Object> listMainPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        /*if (user == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请先登录");
            return modelMap;
        }*/
        try {
            // 获取一级店铺类别列表(即prarentId为空的ShopCategory)
            List<ShopCategory> shopCategoryList = shopCategoryService.listShopCategory(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        try {
            // 获取可用的头条列表(也就是状态为1的头条列表)
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.listHeadLine(headLineCondition);
            for (HeadLine headLine : headLineList) {
                if (!headLine.getLineLink().equals("#")) {
                    headLine.setLineLink(request.getContextPath() + headLine.getLineLink());
                }
            }
            modelMap.put("headLineList", headLineList);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("user", user);
        modelMap.put("success", true);
        return modelMap;
    }
}
