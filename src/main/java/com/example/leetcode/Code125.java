package com.example.leetcode;

/**
 * 125. 验证回文串
 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个回文串。
 * <p>
 * 字母和数字都属于字母数字字符。
 * <p>
 * 给你一个字符串 s，如果它是回文串，返回 true ；否则，返回 false 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入: "A man, a plan, a canal: Panama"
 * 输出：true
 * 解释："amanaplanacanalpanama" 是回文串。
 * 示例 2：
 * <p>
 * 输入："race a car"
 * 输出：false
 * 解释："raceacar" 不是回文串。
 */
public class Code125 {
    public static void main(String[] args) {
        boolean palindrome = isPalindrome("0P");
        System.out.println(palindrome);
    }

    public static boolean isPalindrome(String s) {
        s = s.toUpperCase();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (isAlpha(s.charAt(i)) && isAlpha(s.charAt(j))) {
                if (s.charAt(i) != s.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            while (!isAlpha(s.charAt(i)) && i < j) {
                i++;
            }
            while (!isAlpha(s.charAt(j)) && j > i) {
                j--;
            }

        }
        return true;
    }

    public static boolean isAlpha(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
    }
}
