package com.rabbiter.market.service.goods_management.goods_category;

import com.rabbiter.market.domain.goods_management.goods_category.GoodsCategory;
import com.rabbiter.market.qo.goods_management.goods_category.QueryGoodsCategory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IGoodsCategoryService extends IService<GoodsCategory> {

    /**
     * 更改业务
     * @param goodsCategory
     */
    void updateGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 停用业务
     * @param cid
     */
    void deactivate(Long cid);



    /**
     * 条件查询
     * @param qo
     * @return
     */
    Page<GoodsCategory> queryPageByQo(QueryGoodsCategory qo);

    /**
     * 保存商品分类信息
     * @param category
     */
    void saveGoodsCategory(GoodsCategory category);

    List<Map<String, Object>> getNormalCategoryAll();
}
