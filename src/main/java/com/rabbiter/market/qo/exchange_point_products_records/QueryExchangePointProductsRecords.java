package com.rabbiter.market.qo.exchange_point_products_records;

import com.rabbiter.market.qo.BaseQuery;

public class QueryExchangePointProductsRecords  extends BaseQuery {
    private String cn;
    private Long memberId;
    private String startTime;
    private String endTime;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
