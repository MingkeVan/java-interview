package com.fanmk.leetcode.binarytree;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/symmetric-tree/
 * @date: 2020/4/23 23:17
 */
public class SymmetricTree101 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSymmetric(root.left, root.right);

    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);

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
