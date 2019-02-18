package com.zj.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.ProductExecution;
import com.zj.o2o.entity.Product;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.enums.ProductStateEnum;
import com.zj.o2o.exceptions.ProductOperationException;
import com.zj.o2o.service.ProductCategoryService;
import com.zj.o2o.service.ProductService;
import com.zj.o2o.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-02-16 20:56
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    // 支持上传详情图片的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductListByShop(HttpServletRequest request, Product productCondition,
                                                    int pageIndex, int pageSize) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 空值判断
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            productCondition.setShop(currentShop);
            ProductExecution pe = productService.listProduct(productCondition, pageIndex, pageSize);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("productList", pe.getProductList());
                modelMap.put("count", pe.getCount());
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request, @RequestParam(value = "verifyCodeActual")
            String verifyCodeActual, @RequestParam(value = "thumbnail") CommonsMultipartFile thumbnail) {
        Map<String, Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request, verifyCodeActual)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        // 接收前端参数，商品实体、缩略图、详情图列表、
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        MultipartHttpServletRequest multipartRequest = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        // 从session中获取文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 先判断是否含有文件流,若有则取出相关文件
            if (multipartResolver.isMultipart(request)) {
                handleImage((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        String productStr = request.getParameter("productStr");
        try {
            product = mapper.readValue(productStr, Product.class);

        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                // 从session中获取当前店铺的id,并赋值给product
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 执行添加操作
                ImageHolder imageHolder = null;
                try {
                    imageHolder = new ImageHolder(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProductExecution pe = productService.insertProduct(product, imageHolder, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductById(@RequestParam(value = "productId") Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            // 获取商品信息
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService.
                    listProductCategory(product.getShop().getShopId());
            modelMap.put("success", true);
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProduct(HttpServletRequest request, @RequestParam(value = "verifyCodeActual", required = false)
            String verifyCodeActual, MultipartFile thumbnail,
                                             boolean statusChange) {
        Map<String, Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!statusChange && !CodeUtil.checkVerifyCode(request, verifyCodeActual)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        // 接收前端参数，商品实体、缩略图、详情图列表、
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        MultipartHttpServletRequest multipartRequest = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        // 从session中获取文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 先判断是否含有文件流,若有则取出相关文件
            if (multipartResolver.isMultipart(request)) {
                handleImage((MultipartHttpServletRequest) request, productImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        String productStr = request.getParameter("productStr");
        try {
            // 将从前端获取的string流转换成Product实体
            product = mapper.readValue(productStr, Product.class);

        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 更新只需要确保product不为空即可
        if (product != null) {
            try {
                // 从session中获取当前店铺的id,并赋值给product
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ImageHolder imageHolder = null;
                try {
                    if (thumbnail != null) {
                        imageHolder = new ImageHolder(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
                    }
                } catch (IOException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.toString());
                    return modelMap;
                }
                // 执行更新操作
                ProductExecution pe = productService.updateProduct(product, imageHolder, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private void handleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest;
        multipartRequest = request;
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
                    .getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                // 如果第i个详情文件流为空，则终止循环
                break;
            }
        }
    }
}
