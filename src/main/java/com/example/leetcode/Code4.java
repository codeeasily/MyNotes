package com.example.leetcode;

/**
 * 4.寻找两个正序数组的中位数
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 */
public class Code4 {
    public static void main(String[] args) {

    }

    class Solution1 {
        // 暴力破解,先合并数组，再找中位数
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1.length == 0 && nums2.length == 0) {
                return 0;
            }
            if (nums1.length == 0 || nums2.length == 0) {
                if (nums1.length == 0) {
                    return doFindMedianSortedArrays(nums2);
                } else {
                    return doFindMedianSortedArrays(nums1);
                }
            }
            // 合并数组中位数
            int[] nums = new int[nums1.length + nums2.length];
            int i = 0;
            int j = 0;
            while (i + j < nums.length) {
                if (j >= nums2.length) {
                    while (i < nums1.length) {
                        nums[i + j] = nums1[i];
                        i++;
                    }
                } else {
                    while (i < nums1.length && nums1[i] <= nums2[j]) {
                        nums[i + j] = nums1[i];
                        i++;
                    }
                }
                if (i >= nums1.length) {
                    while (j < nums2.length) {
                        nums[j + i] = nums2[j];
                        j++;
                    }
                } else {
                    while (j < nums2.length && nums2[j] <= nums1[i]) {
                        nums[j + i] = nums2[j];
                        j++;
                    }
                }
            }
            return doFindMedianSortedArrays(nums);
        }

        public double doFindMedianSortedArrays(int[] nums2) {
            if (nums2.length == 1) {
                return nums2[0];
            }
            int len = nums2.length;
            return (nums2[len / 2] + nums2[(len - 1) / 2]) / 2.0;
        }
    }

    class Solution2 {
        // 暴力解法优化，根据索引查找
        public  double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1.length == 0 && nums2.length == 0) {
                return 0;
            }
            if (nums1.length == 0 || nums2.length == 0) {
                if (nums1.length == 0) {
                    return doFindMedianSortedArrays(nums2);
                } else {
                    return doFindMedianSortedArrays(nums1);
                }
            }
            int len = nums1.length + nums2.length;
            // 当两个数组长度之和为偶数时，mi2=mi1+1,当和为奇数时，mi2=mi1
            int mi1 = (len - 1) / 2; // 中位数
            int mi2 = len / 2; // 中位数
            int i = 0;
            int j = 0;
            int index = 0;
            int m1 = 0;
            int m2 = 0;
            while (index <= mi2) {
                if (j == nums2.length) {
                    while (i < nums1.length) {
                        if (index == mi1) {
                            m1 = nums1[i];
                        }
                        if (index == mi2) {
                            m2 = nums1[i];
                            return (m1 + m2) / 2.0;
                        }
                        i++;
                        index++;
                    }
                } else {
                    while (i < nums1.length && nums1[i] <= nums2[j]) {
                        if (index == mi1) {
                            m1 = nums1[i];
                        }
                        if (index == mi2) {
                            m2 = nums1[i];
                            return (m1 + m2) / 2.0;
                        }

                        i++;
                        index++;
                    }
                }
                if (i == nums1.length) {
                    while (j < nums2.length) {
                        if (index == mi1) {
                            m1 = nums2[j];
                        }
                        if (index == mi2) {
                            m2 = nums2[j];
                            return (m1 + m2) / 2.0;
                        }
                        j++;
                        index++;
                    }
                } else {
                    while (j < nums2.length && nums2[j] <= nums1[i]) {
                        if (index == mi1) {
                            m1 = nums2[j];
                        }
                        if (index == mi2) {
                            m2 = nums2[j];
                            return (m1 + m2) / 2.0;
                        }
                        j++;
                        index++;
                    }
                }
            }
            return (m1 + m2) / 2.0;

        }

        private  double doFindMedianSortedArrays(int[] nums2) {
            if (nums2.length == 1) {
                return nums2[0];
            }
            int len = nums2.length;
            return (nums2[len / 2] + nums2[(len - 1) / 2]) / 2.0;
        }
    }
}
