package com.fanmk.leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Combinations77 {
    List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> combine(int n, int k) {
        if (n <= 0 || k <= 0 || n < k) {
            return res;
        }

        getCombinations(n, k, 1, new ArrayList<Integer>());
        return res;
    }

    private void getCombinations(int n, int k, int start, List<Integer> cur) {
        if (cur.size() == k) {
            res.add(new ArrayList<Integer>(cur));
            return;
        }

        // 当前已有cur.size()  还需 k-cur.size()
        // n-start + 1 >= k-cur.size()
        // start <= n - (k-cur.size()) +1
        for (int i = start; i <= n - (k-cur.size()) +1; i++) {
            cur.add(i);
            getCombinations(n, k, i + 1, cur);
            cur.remove(cur.size() - 1);
        }
    }

}
