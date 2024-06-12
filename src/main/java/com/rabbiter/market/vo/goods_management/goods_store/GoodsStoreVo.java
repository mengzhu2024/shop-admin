package com.rabbiter.market.vo.goods_management.goods_store;

import java.io.Serializable;

public class GoodsStoreVo implements Serializable {
    private Long id;
    private String name; //商品名
    private String coverUrl; //商品封面

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
