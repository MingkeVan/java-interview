package com.fanmk.leetcode.backtracking;
import java.util.*;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/permutations-ii/
 *
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
 *
 * Example:
 *
 * Input: [1,1,2]
 * Output:
 * [
 *   [1,1,2],
 *   [1,2,1],
 *   [2,1,1]
 * ]
 * @date: 2020/10/24 22:38
 */
public class PermutationsII47 {
    private  List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        if(nums.length == 0) {
            return res;
        }

        // 1.构建map key为数字 value为次数
        // 由于题目中给出的数组有重复数字，所以为了避免重复遍历，需要首先构建map
        Map<Integer,Integer> map = new HashMap<>();
        for(int num : nums) {
            if(map.get(num) == null) {
                map.put(num,1);
            }
            else {
                map.put(num,map.get(num) +1);
            }
        }

        // 2.使用回溯法暴力循环遍历map
        backtracking(map,nums.length, new ArrayList<Integer>());
        return res;
    }

    private void backtracking(Map<Integer,Integer> map,
                              int total,
                              List<Integer> list){
        if(list.size() == total) {
            res.add(new ArrayList<>(list));
            return;
        }

        for(Map.Entry<Integer,Integer> entry: map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if(value <= 0) {
                continue;
            }
            list.add(key);
            map.put(key,value-1);
            backtracking(map,total,list);
            list.remove(list.size() - 1);
            map.put(key,value);
        }

    }
}
