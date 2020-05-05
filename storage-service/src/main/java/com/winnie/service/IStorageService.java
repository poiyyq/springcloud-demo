package com.winnie.service;

import com.winnie.entity.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IStorageService {
    /**
     * 减库存
     *
     * @param goodsid
     * @param count
     */
    @PostMapping("/deCount")
    ResponseEntity deCount(@RequestParam("goodsid") String goodsid, @RequestParam("count") Integer count);
}
