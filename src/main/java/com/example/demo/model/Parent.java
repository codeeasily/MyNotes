package com.example.demo.model;

/**
 * @author iCoderLad
 * @date 2022/08/26 18:14
 */
public class Parent {
    static {
        System.out.println("父类中的 static 代码块");
    }

    {
        System.out.println("父类中的 no-static 代码块");
    }

    public Parent() {
        System.out.println("父类中的 构造方法");
    }

    public void doSomething() {
        System.out.println("父类中的 方法");
    }
}
