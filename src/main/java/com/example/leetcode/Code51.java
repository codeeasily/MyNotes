package com.example.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 51. N 皇后
 * <p>
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * </p>
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * <p>
 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 * </p>
 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * </p>
 * <img src="https://assets.leetcode.com/uploads/2020/11/13/queens.jpg" />
 * <p>
 * 输入：n = 4
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
 * </p>
 * 输入：n = 1
 * 输出：[["Q"]]
 */
public class Code51 {
    public static void main(String[] args) {
        solveNQueens(4);
    }

    private static void solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        nqueen(n, 0, new int[n], result);
        System.out.println(result);
    }

    /**
     * 从第一行开始，每一行都从第一列开始，直到遍历到最后一行结束
     *
     * @param n   n*n
     * @param row 当前所在行
     * @param arr 存储每一行皇后的位置，相当于坐标（x,y）
     * @param res 结果
     * @return
     */
    public static List<List<String>> nqueen(int n, int row, int[] arr, List<List<String>> res) {
        if (row == n) {
            res.add(convert(n, arr));
            return res;
        }
        for (int col = 0; col < n; col++) {
            //先尝试着把皇后放在这一列
            arr[row] = col;
            //判断和上面行的皇后是否冲突
            if (isOk(row, col, arr)) {
                //迭代,下一行
                nqueen(n, row + 1, arr, res);
            }
        }
        return res;
    }

    private static boolean isOk(int row, int col, int[] arr) {
        for (int i = 0; i < row; i++) {
            //判断上面每行皇后所在列, 如果和当前列相同则为false
            if (arr[i] == col) {
                return false;
            }
            //判断撇和捺方向, 是两个斜率为1和-1的直线, 则他们两个坐标 |y2 - y1| == |x2 - x1|
            if (row - i == col - arr[i] || row - i == arr[i] - col) {
                return false;
            }
        }
        return true;
    }

    private static List<String> convert(int n, int[] arr) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] chars = new char[n];
            for (int j = 0; j < n; j++) {
                if (arr[i] == j) {
                    chars[j] = 'Q';
                } else {
                    chars[j] = '.';
                }
            }
            res.add(String.valueOf(chars));
        }
        return res;
    }

    /*
     * 回朔法
     * 版权声明：本文为CSDN博主「zzzgd816」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/zzzgd_666/article/details/121008450
     */
    static class Nqueen {
        public static void main(String[] args) {
            solveNQueens(4);
        }

        private static void solveNQueens(int n) {
            int nqueen = nqueen(n, 0, new int[n], 0);
            System.out.println("nqueen = " + nqueen);
        }

        /*
         * 回朔法
         * 版权声明：本文为CSDN博主「zzzgd816」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
         * 原文链接：https://blog.csdn.net/zzzgd_666/article/details/121008450
         */

        /**
         * @param n     表示n * n 的格子
         * @param row   当前是多少行
         * @param res   用一个数组,记录每一行的皇后所在的列
         * @param count 结果总数
         * @return
         */
        public static int nqueen(int n, int row, int[] res, int count) {
            if (row == n) {
                //打印
                print(n, res);
                count++;
                System.out.println("-----------");
                return count;
            }
            for (int col = 0; col < n; col++) {
                //先尝试着把皇后放在这一列
                res[row] = col;
                //判断和上面行的皇后是否冲突
                if (isOk(row, col, res)) {
                    //迭代,下一行
                    count = nqueen(n, row + 1, res, count);
                }
            }
            return count;
        }

        private static boolean isOk(int row, int col, int[] res) {
            for (int i = 0; i < row; i++) {
                //判断上面每行皇后所在列, 如果和当前列相同则为false
                if (res[i] == col) {
                    return false;
                }
                //判断撇和捺方向, 是两个斜率为1和-1的直线, 则他们两个坐标 |y2 - y1| == |x2 - x1|
                if (row - i == col - res[i] || row - i == res[i] - col) {
                    return false;
                }
            }
            return true;
        }

        private static void print(int n, int[] res) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (res[i] == j) {
                        System.out.print("Q");
                    } else {
                        System.out.print("*");
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}



