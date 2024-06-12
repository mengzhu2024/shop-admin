package com.rabbiter.market.qo.inventory_management.store;

import com.rabbiter.market.qo.BaseQuery;

public class QueryDetailStorageSituation extends BaseQuery {
    private Long id;
    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
