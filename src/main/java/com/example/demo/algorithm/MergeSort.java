package com.example.demo.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @date 2022/09/13 19:38
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] a = new Random().ints(0,10).distinct().limit(7).toArray();
        System.out.println(Arrays.toString(a));
        sort(a, 0, a.length -1);
        System.out.println(Arrays.toString(a));
    }

    public static int[] sort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边归并排序
            sort(a, low, mid);
            // 右边归并排序
            sort(a, mid + 1, high);
            // 合并两个有序数组
            merge(a, low, mid, high);
        }
        return a;
    }

    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= high) {
            // 对比大小，调整顺序
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 右边剩余元素填充进temp中（因为前面已经归并，剩余的元素必会小于右边剩余的元素）
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 右边剩余元素填充进temp中（因为前面已经归并，剩余的元素必会大于于左边剩余的元素）
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 调整数组顺序
        for (int x = 0; x < temp.length; x++) {
            a[x + low] = temp[x];
        }
    }


}

