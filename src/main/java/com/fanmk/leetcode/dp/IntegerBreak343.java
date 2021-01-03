package com.fanmk.leetcode.dp;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/minimum-path-sum/
 * @date: 2020/12/23 22:44
 * 
 * Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of those integers. Return the maximum product you can get.

Example 1:

Input: 2
Output: 1
Explanation: 2 = 1 + 1, 1 × 1 = 1.
Example 2:

Input: 10
Output: 36
Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
Note: You may assume that n is not less than 2 and not larger than 58.
 */
public class IntegerBreak343 {
    static class Solution {
        int[] mem = null;
        
        public int integerBreak(int n) {
            mem = new int[n+1];

            return breakInteger(n);
        }

        private int breakInteger(int n ) {
            if( n  == 1) {
                return 1;
            }

            if(mem[n] != 0) {
                return mem[n];
            }
            int res =1;
            for(int i = 1; i< n; i++) {
                res = max3(res,i * breakInteger(n-i) , i* (n-i));
            }

            mem[n] = res;
            return mem[n];
        }

        private int max3 (int i, int j, int k) {
            return Math.max(i,Math.max(j,k));
        }
    }

    static class Solution1 {
        int[] mem = null;
        
        public int integerBreak(int n) {
            mem = new int[n+1];
            
            mem[1] = 1;
            
            for(int i = 2; i <= n; i++) {
                for(int j = 1; j<= i/2; j++) {
                    mem[i] = max3(mem[i], j * mem[i-j], j*(i-j));
                }
            }

            return mem[n];
            
        }

        private int max3 (int i, int j, int k) {
            return Math.max(i,Math.max(j,k));
        }
    }

    

    public static void main(String[] args) {
        // System.out.println(new Solution().minimumTotal(12));
    }
}
