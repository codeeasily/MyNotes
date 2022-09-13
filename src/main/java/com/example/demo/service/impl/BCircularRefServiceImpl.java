package com.example.demo.service.impl;

import com.example.demo.service.ACircularRefService;
import com.example.demo.service.BCircularRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @date 2022/08/30 01:08
 */
@Service
public class BCircularRefServiceImpl implements BCircularRefService {
//    @Autowired
//    ACircularRefService aCircularRefService;

    @Override
    public void doNothing() {
//        aCircularRefService.doNothing();
    }
}
