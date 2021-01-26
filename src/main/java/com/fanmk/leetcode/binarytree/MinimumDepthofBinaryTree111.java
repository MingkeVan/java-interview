package com.fanmk.leetcode.binarytree;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/maximum-depth-of-binary-tree/
 * @date: 2020/4/14 23:27
 */
public class MinimumDepthofBinaryTree111 {

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = minDepth(root.left);
        int r = minDepth(root.right);

        //**坑点**:无分支则无叶子结点
        if (l == 0 || r == 0) {
            return Math.max(l, r) + 1;
        }

        return Math.min(l, r) + 1;
    }

    int minDepth = Integer.MAX_VALUE;

    public int minDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root, 1);
        return minDepth;
    }

    private void dfs(TreeNode node, int depth) {
        if (node == null) {
            return;
        }

        //在叶子结点出计算深度
        if (node.left == null && node.right == null) {
            minDepth = Integer.min(minDepth, depth);
        }

        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3);
//        treeNode.left = new TreeNode(2);
//        treeNode.right = new TreeNode(7);
//
//        treeNode.left.left = new TreeNode(4);
//        treeNode.left.right = new TreeNode(5);
//
//
//        treeNode.right.left = new TreeNode(7);
//        treeNode.right.right = new TreeNode(1);
        System.out.println(new MinimumDepthofBinaryTree111().minDepth(treeNode));
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
