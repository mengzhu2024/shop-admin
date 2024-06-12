package com.rabbiter.market.qo.goods_management.point_products;

import com.rabbiter.market.qo.BaseQuery;

public class QueryPointProducts extends BaseQuery {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
