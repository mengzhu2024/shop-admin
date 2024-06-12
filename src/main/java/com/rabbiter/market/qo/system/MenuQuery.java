package com.rabbiter.market.qo.system;

import com.rabbiter.market.qo.BaseQuery;

public class MenuQuery extends BaseQuery {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
