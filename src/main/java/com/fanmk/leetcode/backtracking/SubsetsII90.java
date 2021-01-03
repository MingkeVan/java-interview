package com.fanmk.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/11/10 11:26
 */
public class SubsetsII90 {

    private List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        
        if (nums.length <= 0) {
            return res;
        }

        Arrays.sort(nums);

        generate(nums, 0, new ArrayList<Integer>());
        return res;
    }

    private void generate(int[] nums, int start, List<Integer> list) {
        res.add(new ArrayList(list));

        for(int i = start;i < nums.length; i++) {
            if(i > start && nums[i] == nums[i-1]){
                continue;
            }
            
            list.add(nums[i]);
            generate(nums, i+1, list);
            list.remove(list.size() -1);
        }
    }

}
