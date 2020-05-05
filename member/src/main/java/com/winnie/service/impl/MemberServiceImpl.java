package com.winnie.service.impl;

import com.winnie.entity.ResponseEntity;
import com.winnie.entity.UserEntity;
import com.winnie.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("会员服务接口")
@RestController
@RefreshScope
public class MemberServiceImpl implements IMemberService {
    @Value("${testvalue}")
    private String testvalue;

    @GetMapping("/testvalue")
    public String testvalue(){
        return testvalue;
    }

    @ApiOperation(value = "查询会员")
    @ApiImplicitParam(name = "name", value = "会员姓名", required = true, dataTypeClass = java.lang.String.class)
    @GetMapping("/getMember")
    @Override
    public UserEntity getMember(@RequestParam("name") String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(name);
        userEntity.setAge(20);
        return userEntity;
    }

    @GetMapping("/getDelayMember")
    @Override
    public ResponseEntity getDelayMember() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.success("会员服务延迟1.5s秒返回成功", null);
    }
}
