package com.example.demo.multithreading.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author codeeasily
 * @date 2022/08/02 10:05
 */
@Configuration
public class ExecutorThreadPool {
    @Bean("testExecutor")
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(4, 10, 5, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("testExecutor-%d").build());
    }
}
