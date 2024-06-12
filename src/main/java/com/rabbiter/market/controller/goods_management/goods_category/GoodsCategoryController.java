package com.rabbiter.market.controller.goods_management.goods_category;

import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.goods_management.goods_category.GoodsCategory;
import com.rabbiter.market.qo.goods_management.goods_category.QueryGoodsCategory;
import com.rabbiter.market.service.goods_management.goods_category.IGoodsCategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/goods_management/goods_category")
public class GoodsCategoryController {
    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    /*保存信息接口*/
    @HasPermisson("goods_management:goods_category:save")
    @PostMapping("/save")
    public JsonResult saveGoodsCategory(GoodsCategory category){
        goodsCategoryService.saveGoodsCategory(category);
        return JsonResult.success();
    }

    /*修改接口*/
    @HasPermisson("goods_management:goods_category:update")
    @PostMapping("/update")
    public JsonResult updateGoodsCategory(GoodsCategory category){
        goodsCategoryService.updateGoodsCategory(category);
        return JsonResult.success();
    }
    /*停用*/
    @HasPermisson("goods_management:goods_category:deactivate")
    @PostMapping("/deactivate")
    public JsonResult deactivate(Long cid){
        goodsCategoryService.deactivate(cid);
        return JsonResult.success();
    }
    /*查询信息*/
    @HasPermisson("goods_management:goods_category:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryGoodsCategory qo) {
        Page<GoodsCategory> page = goodsCategoryService.queryPageByQo(qo);
        return JsonResult.success(page);
    }
    @GetMapping("/normalCategoryAll")
    public JsonResult getNormalCategoryAll(){
        List<Map<String ,Object>> list=goodsCategoryService.getNormalCategoryAll();
        return JsonResult.success(list);
    }
}
