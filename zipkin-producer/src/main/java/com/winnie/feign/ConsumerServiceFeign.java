package com.winnie.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "zipkin-consumer",url = "http://127.0.0.1:9000")
public interface ConsumerServiceFeign {
    @GetMapping("/who")
    String who();
}
