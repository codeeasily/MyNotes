package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @date 2022/09/08 10:44
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new Random().ints(0,10).distinct().limit(8).toArray();
        System.out.println(Arrays.toString(arr));
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

}
