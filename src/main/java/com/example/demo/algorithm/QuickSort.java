package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @author iCoderLad
 * @date 2022/09/08 10:01
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new Random().ints(0,10).distinct().limit(8).toArray();
        System.out.println(Arrays.toString(arr));
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        doQuickSort(arr, 0, arr.length - 1);

    }

    private static void doQuickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int l = left;
        int r = right;
        int base = arr[l];
        while (l < r) {
            // 先从右边找比基准数小的
            while (r > l && arr[r] > base) {
                r--;
            }
            // 从左边找比基准数大的
            while (l < r && arr[l] < base) {
                l++;
            }
            if (l < r) {
                int temp = arr[l];
                arr[l] = arr[r];
                arr[r] = temp;
            }
        }
        // 基准数归位 此时l==r
        // arr[r] = base;
        arr[l] = base;
        doQuickSort(arr, left, r - 1);
        doQuickSort(arr, r + 1, right);
    }
}
