package com.winnie.feign;

import com.winnie.fallback.StorageServiceFallback;
import com.winnie.service.IStorageService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "app-storage", fallback = StorageServiceFallback.class)
public interface StorageServiceFeign extends IStorageService {
}
