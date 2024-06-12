package com.rabbiter.market.qo.inventory_management.notice;

import com.rabbiter.market.qo.BaseQuery;

public class QueryNoticeOut extends BaseQuery {
    private String name;
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
