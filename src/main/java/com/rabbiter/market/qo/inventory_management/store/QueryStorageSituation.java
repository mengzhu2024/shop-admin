package com.rabbiter.market.qo.inventory_management.store;

import com.rabbiter.market.qo.BaseQuery;

public class QueryStorageSituation extends BaseQuery {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
