package com.winnie.mapper.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderEntity {
    private int id;
    private String orderid;
    private String userid;
    private String goodsid;

    private Date createTime;
    private String status;

}
