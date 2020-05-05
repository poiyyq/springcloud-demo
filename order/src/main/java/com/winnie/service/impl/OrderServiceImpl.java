package com.winnie.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.winnie.entity.ResponseEntity;
import com.winnie.entity.UserEntity;
import com.winnie.feign.MemberServiceFeign;
import com.winnie.feign.StorageServiceFeign;
import com.winnie.mapper.OrderMapper;
import com.winnie.mapper.entity.OrderEntity;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api("订单服务")
@RestController
public class OrderServiceImpl {
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    StorageServiceFeign storageServiceFeign;

    @ApiOperation("查询会员")
    @ApiImplicitParam(name = "name",value = "姓名",required = true,dataType = "String")
    @GetMapping("/getMember")
    public UserEntity getMember(String name){
        UserEntity member = memberServiceFeign.getMember(name);
        return member;
    }

    /**
     * HystrixCommand 注解默认开始服务隔离（线程池隔离方式）、服务降级、服务熔断
     * @return
     */
    @ApiOperation("查询延迟后的会员")
    @HystrixCommand(fallbackMethod = "getDelayMemberFallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="3000")
    })
    @GetMapping("/getDelayMember")
    public ResponseEntity getDelayMember(){
        ResponseEntity delayMember = memberServiceFeign.getDelayMember();
        return delayMember;
    }


    public ResponseEntity getDelayMemberFallback(){
        return ResponseEntity.error("服务降级，返回一个友好提示", null);
    }


    @GetMapping("selectAll")
    @ApiOperation("查看所有订单")
    public List<OrderEntity> selectAll(){
        List<OrderEntity> orderEntities = orderMapper.selectAll();
        return orderEntities;
    }

    @PostMapping("insertOne")
    @ApiOperation("创建订单")
    @GlobalTransactional(rollbackFor = Exception.class)
    public ResponseEntity insertOne(OrderEntity orderEntity){
        ResponseEntity responseEntity;
        // 1. 下单
        try {
            orderEntity.setCreateTime(new Date());
            orderMapper.insertOne(orderEntity);
            // 2. 减库存
            responseEntity = storageServiceFeign.deCount(orderEntity.getGoodsid(), 1);
        } catch (Exception e) {
            throw  new RuntimeException("服务异常，事务回滚");
        }
        return responseEntity;
    }


}
