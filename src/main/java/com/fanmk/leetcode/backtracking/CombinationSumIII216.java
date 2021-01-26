package com.fanmk.leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/11/10 10:18
 */
public class CombinationSumIII216 {

    private List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> combinationSum3(int k, int n) {

        generate(k, n, 0, 1, new ArrayList<Integer>());
        return res;
    }

    private void generate(int k, int n, int depth, int start, List<Integer> cur) {
        if (depth == k && n == 0) {
            res.add(new ArrayList<Integer>(cur));
            return;
        } else if (depth > k || n < 0) {
            return;
        }

        for (int i = start; i < 10; i++) {
            if (n < i) {
                continue;
            }
            cur.add(i);
            generate(k, n - i, depth + 1, i + 1, cur);
            cur.remove(cur.size() - 1);
        }
    }

}
