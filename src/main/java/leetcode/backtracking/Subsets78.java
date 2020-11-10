package leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/11/10 11:26
 */
public class Subsets78 {

    private List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length <= 0) {
            return res;
        }

        generate(nums, 0, new ArrayList<Integer>());
        return res;
    }

    private void generate(int[] nums, int start, List<Integer> list) {
        res.add(new ArrayList<Integer>(list));

        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);
            generate(nums, i + 1, list);
            list.remove(list.size() - 1);
        }
    }

}
