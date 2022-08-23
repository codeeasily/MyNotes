package com.example.demo.multithreading.juctools;

import java.util.Random;
import java.util.concurrent.Semaphore;

/*
 *一个小桑拿房一次只能容纳5个人
 *
 */
public class SemaphoreTest {
    // 最多允许5个人进入
    private static final Semaphore SEMAPHORE = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i ++){
            int finalI = i;
            new Thread(()->{
                // 获取到令牌
                try {
                    SEMAPHORE.acquire();
                    System.out.println(finalI + "-号成功进入");
                    int time = new Random().nextInt(60);
                    Thread.sleep(time);
                    System.out.println(finalI + "-号待了：" + time + " 分钟后出来了");
                    SEMAPHORE.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, String.valueOf(i)).start();
        }
    }

}