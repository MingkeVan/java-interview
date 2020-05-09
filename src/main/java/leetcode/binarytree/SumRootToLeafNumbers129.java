package leetcode.binarytree;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription:
 * @date: 2020/5/7 22:42
 */
public class SumRootToLeafNumbers129 {
    public int sumNumbers(TreeNode root) {
        return dfs(root, new StringBuilder());
    }

    private int dfs(TreeNode root, StringBuilder sb) {
        if (root == null) {
            return 0;
        }
        int length = sb.length();
        sb.append(root.val);

        if (root.left == null && root.right == null) {
            int num = Integer.parseInt(sb.toString());
            sb.setLength(length);
            return num;
        }

        int left = dfs(root.left, sb);
        int right = dfs(root.right, sb);
        sb.setLength(length);
        return left + right;
    }

    /**
     * 优化版 采用数字减少了字符串转数字的开销，而且更符合题意
     * @param root
     * @return
     */
    public int sumNumbers1(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root, int num) {
        if (root == null) {
            return 0;
        }

        num = num * 10 + root.val;
        if (root.left == null && root.right == null) {
            return num;
        }

        return dfs(root.left, num) + dfs(root.right, num);
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
