package com.rabbiter.market.domain.inventory_management.detail_store_goods;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("t_detail_store_goods")
public class DetailStoreGoods implements Serializable {
    public static final String TYPE_IN="0";
    public static final String TYPE_OUT="1";
    private String cn;
    @TableField("goods_id")
    private Long goodsId;
    @TableField("goods_num")
    private Long goodsNum;
    @TableField("goods_name")
    private String goodsName;
    private String type;
    private Long createid;
    private String createby;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    @TableField("create_time")
    private Date createTime;
    private String info;

    public DetailStoreGoods() {
    }

    public DetailStoreGoods(String cn, Long goodsId, Long goodsNum, String goodsName, String type, Long createid, String createby, Date createTime, String info) {
        this.cn = cn;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.goodsName = goodsName;
        this.type = type;
        this.createid = createid;
        this.createby = createby;
        this.createTime = createTime;
        this.info = info;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
