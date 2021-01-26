package com.fanmk.leetcode.dp;

import java.util.List;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/triangle/
 * @date: 2020/12/23 22:44
 * 
Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

For example, given the following triangle

[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

Note:

Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
 */
public class Triangle120 {
    static class Solution {
        int m = 0;
        int n = 0;

        int[][] arr = null;
        // 子问题为相邻元素最小的
        public int minimumTotal(List<List<Integer>> triangle) {
            m= triangle.size();
            arr = new int[m][m];

            for(int i = 0; i < m; i++) {
                for(int j = 0 ; j < m ; j ++) {
                    arr[i][j] = Integer.MIN_VALUE;
                }
            }
            return dp(triangle,0,0);
        }

        private int dp(List<List<Integer>> triangle,int row,int col) {
            if(row == m-1) {
                return triangle.get(row).get(col);
            }

            if(arr[row][col] != Integer.MIN_VALUE) {
                return arr[row][col];
            }

            int tmp = triangle.get(row).get(col)  +
             Math.min(
                 dp(triangle,row+1,col), 
                 dp(triangle,row+1,col+1 )
                 ); 
                 arr[row][col] = tmp;
            return arr[row][col];
        }
    }

    public static void main(String[] args) {
        // System.out.println(new Solution().minimumTotal(12));
    }
}
