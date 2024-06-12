package com.rabbiter.market.service.goods_management.goods;

import com.rabbiter.market.domain.goods_management.goods.Goods;
import com.rabbiter.market.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.rabbiter.market.domain.inventory_management.notice.NoticeIn;
import com.rabbiter.market.domain.inventory_management.notice.NoticeOut;
import com.rabbiter.market.qo.goods_management.goods.QueryGoods;
import com.rabbiter.market.qo.goods_management.goods_store.QueryGoodsStore;
import com.rabbiter.market.qo.goods_management.statistic_sale.QueryStatisticSale;
import com.rabbiter.market.qo.inventory_management.notice.QueryNoticeIn;
import com.rabbiter.market.qo.inventory_management.notice.QueryNoticeOut;
import com.rabbiter.market.vo.detail_store_goods.notice.NoticeInNotNormalVo;
import com.rabbiter.market.vo.goods.GoodsListVo;
import com.rabbiter.market.vo.goods_management.goods_store.GoodsStoreVo;
import com.rabbiter.market.vo.statistics.sale_management.SalesStatisticsVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IGoodsService extends IService<Goods> {
    /**
     * 分页查询出数据
     * @param qo
     * @return
     */
    Page<GoodsListVo> queryPageByQo(QueryGoods qo);

    void saveGoods(Goods goods, String token);

    void updateGoods(Goods goods, String token);

    List<Map<String, Object>> selected_goodsAll();

    Page<GoodsStoreVo> queryPageGoodsStore(QueryGoodsStore qo);

    GoodsStoreVo queryGoodsStoreById(Long id);

    Page<NoticeIn> queryPageNoticeIn(QueryNoticeIn qo);

    Page<NoticeOut> queryPageNoticeOut_shelves(QueryNoticeOut qo);

    SalesStatisticsVo queryPageStatisticSaleByQo(QueryStatisticSale qo);
}
