package com.fanmk.leetcode.binarytree;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/balanced-binary-tree/
 * @date: 2020/4/23 23:37
 */
public class BalancedBinaryTree110 {
    public boolean isBalanced(TreeNode root) {

        if (root == null) {
            return true;
        }

        int diff = Math.abs(height(root.left) - height(root.right));
        if (diff > 1) {
            return false;
        }

        return isBalanced(root.left) && isBalanced(root.right);
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Integer.max(height(root.left), height(root.right)) + 1;
    }

    // 更快速的方法 只需要遍历一次
    public boolean isBalanced1(TreeNode root) {
        if (root == null) {
            return true;
        }
        int leftH = isBalanced(root.left, 1);
        int rightH = isBalanced(root.right, 1);
        if (leftH == -1 || rightH == -1) {
            return false;
        } else if (leftH > rightH + 1 || rightH > leftH + 1) {
            return false;
        } else {
            return true;
        }
    }

    private int isBalanced(TreeNode root, int h) {
        if (root == null) {
            return h;
        } else {
            int leftH = isBalanced(root.left, h + 1);
            int rightH = isBalanced(root.right, h + 1);
            if (leftH == -1 || rightH == -1) {
                return -1;
            } else if (leftH > rightH + 1 || rightH > leftH + 1) {
                return -1;
            } else {
                return Math.max(leftH, rightH);
            }
        }
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
