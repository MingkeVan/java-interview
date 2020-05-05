package leetcode.binarytree;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * @date: 2020/4/14 23:27
 */
public class MaximumDepthofBinaryTree104 {

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    int maxDepth = 0;

    public int maxDepth1(TreeNode root) {
        dfs(root, 1);
        return maxDepth;
    }

    private void dfs(TreeNode node, int depth) {
        if (node == null) {
            return;
        }

        maxDepth = Math.max(maxDepth, depth);
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
        System.out.println(new MaximumDepthofBinaryTree104().maxDepth1(treeNode));
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
