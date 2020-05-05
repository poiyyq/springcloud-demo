package com.winnie.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageMapper {
    @Update("update t_storage set count = count-#{count} where goodsid = #{goodsid}")
    Long updateCount(String goodsid, Integer count);
}
