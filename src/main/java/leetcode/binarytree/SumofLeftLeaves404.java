package leetcode.binarytree;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription:
 * @date: 2020/4/25 0:01
 */
public class SumofLeftLeaves404 {
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int cur = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {
            cur = root.left.val;
        }

        return cur + sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
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
