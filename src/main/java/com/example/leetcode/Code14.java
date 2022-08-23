package com.example.leetcode;

/**
 * 14. 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 * 示例 1：
 *
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 *
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * 提示：
 *
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 */
public class Code14 {
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"reflower","flow","flight"}));
    }

    public static String longestCommonPrefix(String[] strs) {
        if(strs.length == 0){
            return "";
        }
        // 公共前缀的长度不大于数组中的任意字符串，先随便取一个
        String s = strs[0];
        for(String str: strs){
            if (str.length() < s.length()){
                s = s.substring(0, str.length());
            }
            while(!str.startsWith(s)){
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }
}
