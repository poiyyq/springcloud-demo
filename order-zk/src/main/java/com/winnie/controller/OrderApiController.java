package com.winnie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order")
public class OrderApiController {

    // rpc服务调用方式， rest， feign（负载均衡器）
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("getOrder")
    public String getOrder() {
//        String result = restTemplate.getForObject("http://127.0.0.1:8000/member/getMember", String.class);
        String serviceId = "app-member";
        String result = restTemplate.getForObject("http://"+serviceId+"/member/getMember", String.class);
        return "this is getOrder,订单服务. 查询会员服务结果为：" + result;
    }
}
