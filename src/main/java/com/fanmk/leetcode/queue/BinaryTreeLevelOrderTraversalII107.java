package com.fanmk.leetcode.queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/binary-tree-level-order-traversal-ii/
 * @date: 2020/4/2 23:42
 */
public class BinaryTreeLevelOrderTraversalII107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();

        queue.add(root);
        queue.add(null);
        res.add(new ArrayList<>());

        while (!queue.isEmpty()) {
            TreeNode top = queue.poll();

            if (top == null) {
                if (!queue.isEmpty()) {
                    queue.add(null);
                    res.add(0,new ArrayList<>());
                }
                continue;
            } else {
                res.get(0).add(top.val);
            }

            if (top.left != null)
                queue.add(top.left);
            if (top.right != null)
                queue.add(top.right);
        }

        return res;
    }


    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(7);

        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(5);


        treeNode.right.left = new TreeNode(7);
        treeNode.right.right = new TreeNode(1);
        new BinaryTreeLevelOrderTraversalII107().levelOrderBottom(treeNode);
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
