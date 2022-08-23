package com.example.demo.multithreading.juctools;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static final int COUNT = 5;
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(COUNT);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i ++){
            int finalI = i;
            new Thread(()->{
                try {
                    System.out.println("线程-" + finalI + "-开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程-" + finalI + "-执行完毕");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    COUNT_DOWN_LATCH.countDown();
                }
            }).start();
        }
        try {
            // 阻塞，等待所有线程执行完毕
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有线程已经执行完毕,总耗时(ms)：" + (System.currentTimeMillis() - start));
    }
}