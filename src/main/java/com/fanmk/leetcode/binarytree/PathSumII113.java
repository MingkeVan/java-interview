package com.fanmk.leetcode.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/path-sum-ii/
 * @date: 2020/5/7 22:18
 */
public class PathSumII113 {
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, sum, new ArrayList<>(), res);
        return res;
    }

    private void dfs(TreeNode root, int sum, List<Integer> cur, List<List<Integer>> res) {
        if (root == null) {
            return;
        }

        // 这里直接复制数组开销会变大
        // List<Integer> tmp = new ArrayList<>(cur);
        // tmp.add(root.val);
        cur.add(root.val);

        if (root.left == null && root.right == null) {
            if (sum == root.val) {
                res.add(new ArrayList<>(cur));
            }
            //返回前删除数组末尾元素
            cur.remove(cur.size() - 1);
            return;
        }

        dfs(root.left, sum - root.val, cur, res);
        dfs(root.right, sum - root.val, cur, res);

        //返回前删除数组末尾元素
        cur.remove(cur.size() - 1);
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
