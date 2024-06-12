package com.rabbiter.market.mapper.goods_management.goods;

import com.rabbiter.market.domain.goods_management.goods.Goods;
import com.rabbiter.market.domain.inventory_management.notice.NoticeIn;
import com.rabbiter.market.domain.inventory_management.notice.NoticeOut;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    int getNoticeInTotalCount(Map<String, Object> map);

    List<NoticeIn> getNoticePageList(Map<String, Object> map);

    int getNoticeOutShelvesTotalCount(Map<String, Object> map);

    List<NoticeOut> getNoticeShelvesPageList(Map<String, Object> map);
    Long queryPageStatisticSaleByQo(String name);
}
