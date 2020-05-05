package com.winnie.controller;

import com.winnie.entity.UserEntity;
import com.winnie.feign.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    // rpc服务调用方式， rest， feign（负载均衡器）
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private MemberServiceFeign memberApiFeign;

    @RequestMapping("/getMember")
    public UserEntity getMember(@RequestParam String name){
        return memberApiFeign.getMember(name);
    }

    @RequestMapping("/getOrder")
    public String getOrder() {
//        String result = restTemplate.getForObject("http://127.0.0.1:8000/member/getMember", String.class);
        String serviceId = "app-member";
        String result = restTemplate.getForObject("http://"+serviceId+"/member/getMember", String.class);
        return "this is getOrder,订单服务. 查询会员服务结果为：" + result;
    }

    @RequestMapping("/getServiceInstance")
    public String getServiceInstance(){
        List<ServiceInstance> instances = discoveryClient.getInstances("app-member");
        StringBuilder sb = new StringBuilder();
        instances.forEach(instance->{
            sb.append(instance.getUri().toString()) ;
        });
        return sb.toString();
    }
}
