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

    /**
     * 优化版 采用stringbuilder 减少创建字符串的耗时
     * @param root
     * @return
     */
    public List<String> binaryTreePaths1(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfs(root, res, new StringBuilder());
        return res;
    }

    private void dfs(TreeNode root, List<String> list, StringBuilder sb) {
        if (root == null) {
            return;
        }
        int length = sb.length();

        if (root.left == null && root.right == null) {
            list.add(sb.append(root.val).toString());
            sb.setLength(length);
            return;
        }

        sb.append(root.val).append("->");
        dfs(root.left, list, sb);
        dfs(root.right, list, sb);
        // 退出本层递归时，需要将字符串还原为初始状态
        sb.setLength(length);
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
