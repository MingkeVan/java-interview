package leetcode.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 * @date: 2020/5/9 18:41
 */
public class KthSmallestElementinaBST {

    /**
     * 从二分搜索树中查找第k小的元素,有两种解法：
     * *二分法 {@link #kthSmallest(TreeNode, int)}
     * *中序遍历法 {@link #kthSmallest1(TreeNode, int)}
     *
     * <p>
     * 求出当前根节点左子树的节点总数 {@code count}，
     * 若count == k - 1，说明第k小的元素就是当前根节点
     * 若count < k - 1，说明第k小的元素在右子树上，只需要在右子树上寻找第k-1-count小的数
     * 若count > k - 1，说明第k小的元素在左子树上，只需要在左子树上寻找第k小的数*
     * </p>
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        int count = count(root.left);
        if (k - 1 == count) {
            return root.val;
        }
        // 左子树数量不足
        if (count < k - 1) {
            return kthSmallest(root.right, k - 1 - count);
        }

        return kthSmallest(root.left, k);
    }

    /**
     * 计算以root为根节点的二叉搜索树包含的节点总数
     *
     * @param root
     * @return
     */
    private int count(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + count(root.left) + count(root.right);
    }

    /**
     * 中序遍历解法
     *
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest1(TreeNode root, int k) {
        return dfs(root, k, new ArrayList<>()).val;
    }

    private TreeNode dfs(TreeNode root, int k, List<TreeNode> list) {
        if (root == null) {
            return null;
        }

        TreeNode left = dfs(root.left, k, list);
        if (left != null) {
            return left;
        }

        list.add(root);
        if (list.size() == k) {
            return list.get(k - 1);
        }

        TreeNode right = dfs(root.right, k, list);
        if (right != null) {
            return right;
        }

        return null;
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
