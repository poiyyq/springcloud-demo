package com.winnie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberApiController {
    @Value("${server.port}")
    private Integer port;
    @RequestMapping("getMember")
    public String getMember(){
        return "this is member, 我是会员服务。当前端口号为" + port;
    }
}
