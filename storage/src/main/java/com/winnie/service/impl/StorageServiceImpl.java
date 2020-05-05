package com.winnie.service.impl;

import com.winnie.entity.ResponseEntity;
import com.winnie.mapper.StorageMapper;
import com.winnie.service.IStorageService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("库存服务")
@RestController
public class StorageServiceImpl implements IStorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    @ApiOperation("减库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsid", value = "商品id", type = "String", required = true),
            @ApiImplicitParam(name = "count", value = "减多少库存数", type = "int", required = true)
    })
    @GlobalTransactional(rollbackFor = Exception.class)
    @PostMapping("/deCount")
    public ResponseEntity deCount(String goodsid, Integer count) {
        Long result = storageMapper.updateCount(goodsid, count);
        if (result <= 0) {
            throw new RuntimeException("更新失败");
        }
        return ResponseEntity.success("更新成功");
    }
}
