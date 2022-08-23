package com.example.demo.multithreading.juctools;

import lombok.Getter;

import java.util.concurrent.Exchanger;

/**
 * 小红和小明是好朋友
 * 小红想知道小明的小秘密
 * 小明也想知道小红的小秘密
 * 于是他们打算互相交换自己的小秘密
 */
public class ExchangerTest {
    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) {
        new ExchangerTask("小红", EXCHANGER, "小强竟然会怕毛毛虫🐛").start();
        new ExchangerTask("小明", EXCHANGER, "小刚竟然会怕青蛙🐸").start();
    }
}

@Getter
class ExchangerTask extends Thread {
    private String secret;
    private Exchanger<String> exchanger;

    public ExchangerTask(String name, Exchanger<String> exchanger, String secret) {
        this.secret = secret;
        this.exchanger = exchanger;
        this.setName(name);
        System.out.println(name + "的秘密是：" + secret);
    }

    @Override
    public void run() {
        try {
            String exchange = exchanger.exchange(this.secret);
            System.out.println(this.getName() + "-交换得到的秘密是：" + exchange);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
