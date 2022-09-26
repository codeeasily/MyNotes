package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 
 * @date 2022/09/08 10:44
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new Random().ints(0,10).distinct().limit(8).toArray();
        System.out.println(Arrays.toString(arr));
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int current = a[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && current < a[preIndex]){
                a[preIndex + 1] = a[preIndex];
                preIndex --;
            }
            a[preIndex + 1] = current;
        }
        String r = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(","));
        System.out.println(r);
    }

}
