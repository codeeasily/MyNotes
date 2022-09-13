package com.example.demo.model;

/**
 * 
 * @date 2022/08/26 18:14
 */
public class Son extends Parent {
    static {
        System.out.println("子类中的 static 代码块");
    }

    {
        System.out.println("子类中的 no-static 代码块");
    }

    public Son() {
        System.out.println("子类中的 构造方法");
    }

    @Override
    public void doSomething() {
        System.out.println("子类中的 doSomething");
    }

}
