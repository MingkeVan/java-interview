package leetcode.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/binary-tree-paths/
 * @date: 2020/4/27 20:31
 */
public class BinaryTreePaths257 {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfs(root, res, "");
        return res;
    }

    private void dfs(TreeNode root, List<String> list, String s) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            list.add(s + root.val);
            return;
        }

        dfs(root.left, list, s + root.val + "->");
        dfs(root.right, list, s + root.val + "->");
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
