package com.example.demo.multithreading.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;

/**
 * @author codeeasily
 * @date 2022/08/02 10:04
 */
@Component
public class CommonExecutorAsync {
    // 用指定的线程池
    @Async("testExecutor")
    public CompletableFuture<String> doTaskOne() {
        return doTask("One");
    }

    @Async("testExecutor")
    public CompletableFuture<String> doTaskTwo() {
        return doTask("Two");
    }


    @Async("testExecutor")
    public CompletableFuture<String> doTaskThree() {
        return doTask("Three");
    }

    private CompletableFuture<String> doTask(String name) {
        System.out.println("执行任务-" + name + " thread:" + Thread.currentThread().getName());
        StopWatch stopwatch = new StopWatch("任务" + name);
        stopwatch.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stopwatch.stop();
        System.out.println("任务-" + name + " 耗时(ms)：" + stopwatch.getTotalTimeMillis());
        return CompletableFuture.completedFuture("任务" + name + "完成");
    }
}
