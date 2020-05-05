package com.winnie.service;

import com.winnie.entity.ResponseEntity;
import com.winnie.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IMemberService {
    @RequestMapping("/getMember")
    public UserEntity getMember(@RequestParam("name") String name);

    /**
     * 获取延迟后的会员，有一定的延迟时间，用于验证服务雪崩效应
     */
    @RequestMapping("/getDelayMember")
    public ResponseEntity getDelayMember();

}
