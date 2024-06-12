package com.rabbiter.market.qo.goods_management.goods_store;

import com.rabbiter.market.qo.BaseQuery;

public class QueryGoodsStore  extends BaseQuery {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
