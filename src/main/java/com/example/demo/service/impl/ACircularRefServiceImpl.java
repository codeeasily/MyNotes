package com.example.demo.service.impl;

import com.example.demo.service.ACircularRefService;
import com.example.demo.service.BCircularRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @date 2022/08/30 01:07
 */
@Service
public class ACircularRefServiceImpl implements ACircularRefService {
    @Autowired
    BCircularRefService bCircularRefService;

    @Override
    public void doNothing() {
        bCircularRefService.doNothing();
    }
}
