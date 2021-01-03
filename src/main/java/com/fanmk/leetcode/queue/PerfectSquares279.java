package com.fanmk.leetcode.queue;

import java.util.LinkedList;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/perfect-squares (利用队列的bfs解法)
 * @date: 2020/4/4 22:47
 * @see com.fanmk.leetcode.dp.PerfectSquares279 动态规划解法
 */
public class PerfectSquares279 {
    static class Solution {
        //dp解法 fn = f(n-i*i) +1;
        public int numSquares(int n) {
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(new Node(n, 0));

            //记录节点是否访问过，因为层次遍历，节点加入队列的时候就是step最短的时候，后续无需再加入
            boolean[] visited = new boolean[n + 1];
            visited[n] = true;

            while (!queue.isEmpty()) {
                Node cur = queue.remove();

                int i = 1;
                int tmp;
                while ((tmp = cur.val - i * i) >= 0) {
                    if (tmp == 0) {
                        return cur.step + 1;
                    }

                    if (!visited[tmp]) {
                        queue.add(new Node(tmp, cur.step + 1));
                        visited[tmp] = true;
                    }
                    i++;
                }
            }

            return -1;
        }
    }

    static class Node {
        int val;
        int step;

        Node(int val, int step) {
            this.step = step;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numSquares(12));
    }
}
