package com.winnie.fallback;

import com.winnie.entity.ResponseEntity;
import com.winnie.entity.UserEntity;
import com.winnie.feign.MemberServiceFeign;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceFallback implements MemberServiceFeign {
    @Override
    public UserEntity getMember(String name) {
        return null;
    }

    @Override
    public ResponseEntity getDelayMember() {
        return ResponseEntity.error("服务响应超时，请稍后重试。采用class方式声明服务降级",null);
    }
}
