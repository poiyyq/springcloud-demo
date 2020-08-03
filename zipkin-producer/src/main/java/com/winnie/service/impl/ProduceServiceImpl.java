package com.winnie.service.impl;

import com.winnie.service.ProduceService;
import org.springframework.stereotype.Service;

@Service
public class ProduceServiceImpl implements ProduceService {
    @Override
    public String hello() {
        return "i am producer";
    }
}
