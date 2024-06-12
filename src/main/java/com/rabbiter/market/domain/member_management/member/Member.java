package com.rabbiter.market.domain.member_management.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@TableName("t_member")
public class Member implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Long integral;
    private String info;

    public Member() {
    }

    public Member(Long id, String name, String phone, String email, Long integral, String info) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.integral = integral;
        this.info = info;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
