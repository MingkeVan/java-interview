package leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/perfect-squares/ （动态规划解法）
 * @date: 2020/4/4 22:44
 * @see leetcode.queue.PerfectSquares279（队列解法） 通过队列遍历图解决
 */
public class PerfectSquares279 {
    static class Solution {
        //dp解法 fn = f(n-i*i) +1;
        public int numSquares(int n) {
            int[] dp = new int[n + 1];
            dp[0] = 0;

            List<Integer> list = generateList(n);

            for (int i = 1; i <= n; i++) {
                dp[i] = Integer.MAX_VALUE;
                for (Integer square : list) {
                    if (i < square) {
                        break;
                    }
                    dp[i] = Math.min(dp[i - square] + 1, dp[i]);
                }
            }

            return dp[n];
        }

        private List<Integer> generateList(int n) {
            int i = 1;
            int tmp;

            List<Integer> res = new ArrayList<>();
            while ((tmp = i * i) <= n) {
                res.add(tmp);
                i++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numSquares(12));
    }
}
