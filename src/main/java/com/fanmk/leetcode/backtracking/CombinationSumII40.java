package com.fanmk.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/11/10 9:50
 */
public class CombinationSumII40 {

    private List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        Arrays.sort(candidates);
        generate(candidates, target, 0, new ArrayList<Integer>());
        return res;
    }

    private void generate(int[] candidates, int target, int start, List<Integer> cur) {
        if (target == 0) {
            res.add(new ArrayList<Integer>(cur));
            return;
        }
        if (target < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if (target < candidates[i]) {
                continue;
            }
            cur.add(candidates[i]);
            generate(candidates, target - candidates[i], i + 1, cur);
            cur.remove(cur.size() - 1);
        }


    }
}
