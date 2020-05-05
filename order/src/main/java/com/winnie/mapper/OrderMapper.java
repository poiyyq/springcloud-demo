package com.winnie.mapper;

import com.winnie.mapper.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("select * from t_order")
    List<OrderEntity> selectAll();

    @Insert("insert into t_order(orderid,userid,goodsid,create_time) values(#{orderid},#{userid},#{goodsid},#{createTime})")
    void insertOne(OrderEntity orderEntity);
}
