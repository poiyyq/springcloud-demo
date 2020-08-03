package com.winnie.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "zipkin-producer", url = "http://127.0.0.1:9002")
public interface ProduceServiceFeign {
    @GetMapping("/hello")
    String hello();
}
