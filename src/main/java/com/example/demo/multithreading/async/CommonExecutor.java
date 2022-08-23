package com.example.demo.multithreading.async;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author codeeasily
 * @date 2022/08/02 09:51
 */
@Component
public class CommonExecutor {
    public void doTaskOne() {
        doTask("One");
    }

    public void doTaskTwo() {
        doTask("Two");
    }


    public void doTaskThree() {
        doTask("Three");
    }

    private void doTask(String name) {
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
    }
}
