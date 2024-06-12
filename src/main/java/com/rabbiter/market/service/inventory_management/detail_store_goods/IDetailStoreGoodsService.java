package com.rabbiter.market.service.inventory_management.detail_store_goods;

import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.qo.inventory_management.detail_store_goods.QueryDetailStoreGoods;
import com.rabbiter.market.qo.inventory_management.detail_store_goods.QueryDetailStoreGoodsOut;
import com.rabbiter.market.vo.detail_store_goods.DetailStoreGoodsOutVo;
import com.rabbiter.market.vo.detail_store_goods.DetailStoreGoodsVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IDetailStoreGoodsService extends IService<DetailStoreGoods> {


    void saveIn(DetailStoreGoods detailStoreGoods, String token);

    Page<DetailStoreGoodsVo> queryPageByQoIn(QueryDetailStoreGoods qo);

    void delIn(String cn);

    Page<DetailStoreGoodsOutVo> queryPageByQoOut(QueryDetailStoreGoodsOut qo);

    Map<String ,Object> initOutOptions();

}
