package com.fanmk.leetcode.dp;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/minimum-path-sum/
 * @date: 2020/12/23 22:44
 * 
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

 

Example 1:


Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
Example 2:

Input: grid = [[1,2,3],[4,5,6]]
Output: 12
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 200
0 <= grid[i][j] <= 100 */
public class MinimumPathSum64 {
    static class Solution {
        int m = 0;
        int n = 0;
        int[][] arr = null;

        // 子问题为相邻元素最小的
        public int minPathSum(int[][] grid) {
            m = grid.length;
            n = grid[0].length;
            arr = new int[m][n];

            for(int i = 0; i < m; i++) {
                for(int j = 0 ; j < n ; j ++) {
                    arr[i][j] = -1;
                }
            }
            return dp(grid,0,0);
        }

        private int dp(int[][] grid,int row,int col) {
            if(row == m-1 && col == n-1) {
                
                return grid[row][col];
            }

            if(row > m-1 || col > n-1) {
                return Integer.MAX_VALUE;
            }

            if(arr[row][col] != -1) {
                return arr[row][col];
            }
            
            arr[row][col] = grid[row][col] + Math.min(dp(grid,row,col+1),dp(grid,row+1,col));
            return arr[row][col];
        }
    }

    

    public static void main(String[] args) {
        // System.out.println(new Solution().minimumTotal(12));
    }
}
