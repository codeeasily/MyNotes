package com.example.demo.multithreading.juctools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/*
 * 运动员百米赛跑
 * 听到枪响，运动员开始跑
 * 运动员跑到终点保存成绩
 * 所有运动员跑到终点后，成绩排序输出
 */
public class CyclicBarrierExample {
    //运动员数量量
    private static int SPORTSMAN_COUNT = 10;
    private static final Random RANDOM = new Random();
    private static List<Sportsman> SPORTS_SCORE = new ArrayList<>(SPORTSMAN_COUNT);

    private static final CyclicBarrier START = new CyclicBarrier(SPORTSMAN_COUNT, () -> {
        System.out.println("********所有运动员准备完毕********");
        System.out.println("鸣枪。。。");
    });
    private static final CyclicBarrier STOP = new CyclicBarrier(SPORTSMAN_COUNT, () -> {
        System.out.println("********所有运动员都跑到终点了********");
        SPORTS_SCORE.sort(Comparator.comparingInt(Sportsman::getScore));
        System.out.println("排名成绩单(单位ms)：" + SPORTS_SCORE);
    });


    public static void main(String[] args) {
        for (int i = 1; i <= SPORTSMAN_COUNT; i++) {
            new Thread(new RunTask(i + "号")).start();
        }
    }

    /**
     * 运动员类
     */
    static class Sportsman {
        private String name;
        private int score;

        public Sportsman(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            if (obj instanceof Sportsman) {
                result = ((Sportsman) obj).getScore() == this.score;
            }
            return result;
        }

        @Override
        public String toString() {
            return this.name + ":" + this.score;
        }

        public int getScore() {
            return score;
        }
    }

    static class RunTask implements Runnable {
        private String name;

        public RunTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                // 运动员准备就绪的逻辑,准备readyTime秒
                int readyTime = RANDOM.nextInt(1000);
                System.out.println(name + "：我需要" + readyTime + "秒的时间准备。");
                try {
                    Thread.sleep(readyTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + "：我已经准备完毕！");
                // 等待鸣枪开始
                try {
                    START.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + "：开跑...");
                int costTime = RANDOM.nextInt(500);
                try {
                    Thread.sleep(costTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + "：开跑到达终点。成绩:" + costTime + "ms");
                SPORTS_SCORE.add(new Sportsman(name, costTime));
                // 等待所有运动员跑到终点进行成绩排名
                STOP.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}