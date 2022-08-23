package com.example.demo.multithreading;

/**
 * 三个线程按顺序打印ABC
 */
public class PrintABC {

    public static void main(String[] args) {
        printByJoin();
    }

    public static void printByJoin(){
        Thread thread1 = new Thread(() -> {
            System.out.println("A");

        });

        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("B");

        });

        Thread thread3 = new Thread(() -> {
            try {
                thread2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("C");

        });
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
