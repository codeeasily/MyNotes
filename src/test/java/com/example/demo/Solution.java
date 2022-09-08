package com.example.demo;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 5; i ++){
            set.add(new Random().nextInt(5));
        }
        System.out.println(set);
        for (Integer integer : set) {
            System.out.println(integer);
        }

    }


}
