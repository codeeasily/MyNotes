package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

    static {
        System.out.println("静态代码块 先执行");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("启动类 main 入口");
        SpringApplication.run(DemoApplication.class, args);


    }
}
