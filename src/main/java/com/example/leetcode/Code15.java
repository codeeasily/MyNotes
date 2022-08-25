package com.example.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 三数之和
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 示例 2：
 *
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 示例 3：
 *
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 */
public class Code15 {
    public static void main(String[] args) {
        List<List<Integer>> lists = threeSum(new int[]{-10, -3, -8, -3, 4, -1, -2, -4, -8, -5});
        System.out.println(lists);
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        nums = Arrays.stream(nums).sorted().toArray();
        List<List<Integer>> result = new ArrayList<>();
        int splitIndex = 0; // 记录排序后第一个非负数的位置
        while (splitIndex < nums.length && nums[splitIndex] < 0) {
            splitIndex++;
        }

        // 没有负数  只有0 和正数
        if (splitIndex == 0) {
            if (nums[1] + nums[2] == 0) {
                result.add(Arrays.asList(0, 0, 0));
            }
            return result;
        }
        // 全是负数
        if (splitIndex == nums.length) {
            return result;
        }
        // 没有正数 只有0和负数
        if (nums[nums.length - 1] == 0) {
            if (nums[nums.length - 2] + nums[nums.length - 3] == 0) {
                result.add(Arrays.asList(0, 0, 0));
            }
            return result;
        }

        int zIndex = splitIndex;
        int zeroCount = 0;
        while (nums[zIndex] == 0) {
            zIndex++;
            zeroCount++;
        }
        int fIndex = splitIndex - 1;
        // 如果存在0,找相反数
        if (zeroCount > 0) {
            if (zeroCount >= 3) {
                result.add(Arrays.asList(0, 0, 0));
            }
            sumZero(nums, fIndex, zIndex, result);
        }

        // 两个负数加两个正数等于0情况
        sumF(nums, fIndex, zIndex, result);

        // 两个正数加两个负数等于0的情况
        sumZ(nums, fIndex, zIndex, result);
        return result;
    }

    public static void sumZero(int[] nums, int fIndex, int zIndex, List<List<Integer>> result) {
        int pre = 0;
        for (int fi = fIndex; fi >= 0; fi--) {
            if (nums[fi] == pre) {
                continue;
            }
            pre = nums[fi];
            findZero(fi, zIndex, nums, result);
        }
    }

    private static void findZero(int fi, int zi, int[] nums, List<List<Integer>> result) {
        for (; zi < nums.length; zi++) {
            if (nums[fi] + nums[zi] == 0) {
                result.add(Arrays.asList(nums[fi], 0, nums[zi]));
                break;
            }
        }
    }

    public static void sumF(int[] nums, int fIndex, int zIndex, List<List<Integer>> result) {
        int pre = 0;
        for (int zi = zIndex; zi < nums.length; zi++) {
            if (nums[zi] == pre) {
                continue;
            }
            pre = nums[zi];
            findF(fIndex, zi, nums, result);
        }
    }

    private static void findF(int fIndex, int zi, int[] nums, List<List<Integer>> result) {
        int pre = 0;
        for (int fi = fIndex; fi > 0; fi--) {
            if (nums[fi] == pre) {
                continue;
            }
            pre = nums[fi];
            doFindF(zi, fi, nums, result);
        }
    }

    private static void doFindF(int zi, int fi, int[] nums, List<List<Integer>> result) {
        int pre = 0;
        for (int fk = fi - 1; fk >= 0; fk--) {
            if (nums[fk] == pre) {
                continue;
            }
            pre = nums[fk];
            int sum = nums[fk] + nums[fi] + nums[zi];
            if (sum == 0) {
                result.add(Arrays.asList(nums[fk], nums[fi], nums[zi]));
            } else if (sum < 0) {
                break;
            }
        }
    }

    public static void sumZ(int[] nums, int fIndex, int zIndex, List<List<Integer>> result) {
        int pre = 0;
        for (int fi = fIndex; fi >= 0; fi--) {
            if (nums[fi] == pre) {
                continue;
            }
            pre = nums[fi];
            findZ(fi, zIndex, nums, result);
        }
    }

    public static void findZ(int fIndex, int zIndex, int[] nums, List<List<Integer>> result) {
        int pre = 0;
        for (int zi = zIndex; zi < nums.length - 1; zi++) {
            if (nums[zi] == pre) {
                continue;
            }
            pre = nums[zi];
            doFindZ(fIndex, zi, nums, result);
        }
    }

    public static void doFindZ(int fIndex, int zi, int[] nums, List<List<Integer>> result) {
        int pre = 0;
        for (int zk = zi + 1; zk < nums.length; zk++) {
            if (nums[zk] == pre) {
                continue;
            }
            pre = nums[zk];
            int sum = nums[fIndex] + nums[zi] + nums[zk];
            if (sum == 0) {
                result.add(Arrays.asList(nums[fIndex], nums[zi], nums[zk]));
            } else if (sum > 0) {
                break;
            }
        }
    }

}