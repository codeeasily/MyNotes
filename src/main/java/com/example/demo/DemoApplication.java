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

    public static void main(String[] args) {
        System.out.println("启动类 main 入口");
//        System.setProperty("spring.profiles.active","dev");
//        SpringApplication.run(DemoApplication.class, args);
        SpringApplication application = new SpringApplication(DemoApplication.class);
        // 设置允许循环引用（2.6版本之后默认为false）
        application.setAllowCircularReferences(true);
        application.run(args);
    }

}
