package com.winnie.fallback;

import com.winnie.entity.ResponseEntity;
import com.winnie.entity.UserEntity;
import com.winnie.feign.MemberServiceFeign;
import com.winnie.feign.StorageServiceFeign;
import org.springframework.stereotype.Component;

/**
 * 服务降级
 */
@Component
public class StorageServiceFallback implements StorageServiceFeign{

    @Override
    public ResponseEntity deCount(String goodsid, Integer count) {
        throw new RuntimeException("减库存失败，不启动服务降级，直接抛出异常，让分布式事务管理器TC-server自动回滚");
    }
}
