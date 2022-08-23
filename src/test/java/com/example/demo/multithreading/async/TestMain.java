package com.example.demo.multithreading.async;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

/**
 * @author codeeasily
 * @date 2022/08/02 09:52
 */
@SpringBootTest
public class TestMain {

    @Autowired
    private CommonExecutor commonExecutor;

    @Test
    public void test() {
        StopWatch stopwatch = new StopWatch("同步执行");
        stopwatch.start();
        commonExecutor.doTaskOne();
        commonExecutor.doTaskTwo();
        commonExecutor.doTaskThree();
        stopwatch.stop();
        System.out.println("同步执行 总耗时(ms)：" + stopwatch.getTotalTimeMillis());

    }


}
