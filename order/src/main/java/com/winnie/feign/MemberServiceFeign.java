package com.winnie.feign;

import com.winnie.fallback.MemberServiceFallback;
import com.winnie.service.IMemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "app-member", fallback = MemberServiceFallback.class)
public interface MemberServiceFeign extends IMemberService {
    // @FeignClient("app-member") 一定是在调用方编程，为了方便后续服务降级
}
