package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @author iCoderLad
 * @date 2022/09/08 10:28
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new Random().ints(0,10).distinct().limit(8).toArray();
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void bubbleSort(int[] arr) {
        boolean flg = true;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    flg = false;
                }
            }
            // 说明没有发生交换，已经是有序的了
            if (flg) {
                return;
            }
        }
    }
}
