package com.example.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 *
 *
 * 示例 1:
 *
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class Code3 {
    public static void main(String[] args) {
        int len = lengthOfLongestSubstring2("abcabcadef");
        System.out.println(len);
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s.length() < 2) {
            return s.length();
        }
        HashMap<Integer, String> subMap = new HashMap();
        char[] strArr = s.toCharArray();
        for (int i = 0; i < strArr.length; i++) {
            // 存储本次遍历的元素
            List<Character> tmp = new ArrayList<>();
            tmp.add(strArr[i]);
            for (int j = i + 1; j < strArr.length; j++) {
                char b = strArr[j];
                if (tmp.contains(b)) { // 遇到重复的元素，本次遍历结束
                    subMap.put(j - i, tmp.toString());
                    break;
                }
                tmp.add(b);
            }
            subMap.put(tmp.size(), tmp.toString());
        }
        List<Integer> keys = new ArrayList<>(subMap.keySet());
        keys.sort((o1, o2) -> o2 - o1);
        return keys.get(0);
    }

    // 滑动窗口一
    public static int lengthOfLongestSubstring2(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128]; // 存储下标值
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        // 字符串的长度
        int len = s.length();
        int res = 0;
        int start = 0;// 开始位置
        for (int i = 0; i < len; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);// 如果已经出现过，后移一位，起始位置+1
            res = Math.max(res, i - start + 1); // res记录的是最长的子串的长度
            last[index] = i;
        }
        return res;
    }

    // 滑动窗口二
    public int lengthOfLongestSubstring3(String s) {
        if (s.length() == 0) {
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0; // 最大长度
        int left = 0; // 窗口起始位置
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) { // 遍历到重复元素，比如abcad 遍历到第二个a时
                left = Math.max(left, map.get(s.charAt(i)) + 1); // 窗口位置后移一位left=1，即从b开始
            }
            map.put(s.charAt(i), i); //保存当前元素和其下标
            max = Math.max(max, i - left + 1);// 记录窗口的最大长度
        }
        return max;

    }

}
