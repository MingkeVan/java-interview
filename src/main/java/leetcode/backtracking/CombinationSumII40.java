package leetcode.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/combination-sum-ii/
 * <p>
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.
 * <p>
 * Each number in candidates may only be used once in the combination.
 * <p>
 * Note: The solution set must not contain duplicate combinations.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 * Example 2:
 * <p>
 * Input: candidates = [2,5,2,1,2], target = 5
 * Output:
 * [
 * [1,2,2],
 * [5]
 * ]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i] <= 50
 * 1 <= target <= 30
 */
public class CombinationSumII40 {

    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < candidates.length; i++) {
            if (map.containsKey(candidates[i])) {
                map.put(candidates[i], map.get(candidates[i]) + 1);
            } else {
                map.put(candidates[i], 1);
            }
        }

        generateCombination(map, target, 0, new ArrayList<>());
        return res;
    }

    private void generateCombination(Map<Integer, Integer> candidates, int target, int start, List<Integer> cur) {
        if (target == 0) {
            res.add(new ArrayList<>(cur));
            return;
        }
        if (target < 0) {
            return;
        }

        System.out.println(candidates);
        Object[] arr =  candidates.entrySet().toArray();
        for (int i = start; i < arr.length; i++) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>)arr[i];
            if (entry.getKey() <= target && entry.getValue() > 0) {
                int key = entry.getKey();
                int value = entry.getValue();
                cur.add(key);
                candidates.put(key, value - 1);

                generateCombination(candidates, target - key, i, cur);
                cur.remove(cur.size() - 1);
                candidates.put(key, value);
            }
        }
    }

    public static void main(String[] args) {
        CombinationSumII40 combinationSumII40 = new CombinationSumII40();
        int[] arr = {10, 1, 2, 7, 6, 1, 5};
        combinationSumII40.combinationSum2(arr, 8);
    }

}
