package leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/minimum-path-sum/
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
public class DecodeWays91 {

    static class Solution {
        
        public int numDecodings(String s) {
            
            int n = s.length();
            int[] dp = new int[n+1];
            dp[0] = 1;
            dp[1] = s.charAt(0) == '0' ? 0 : 1;

            for(int i = 2; i <= n; i++) {
                if(s.charAt(i-1) != '0') {
                    dp[i] += dp[i-1];
                }

                int num = Integer.valueOf(s.substring(i-2,i));
                
                if(num >= 10 && num <=26) {
                    dp[i] += dp[i-2];
                }
                
            }

            return dp[n];
        }
    }

    

    public static void main(String[] args) {
        // System.out.println(new Solution().minimumTotal(12));
    }
}
