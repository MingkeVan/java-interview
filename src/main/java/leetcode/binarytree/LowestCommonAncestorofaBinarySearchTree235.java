package leetcode.binarytree;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * @date: 2020/5/9 17:03
 */
public class LowestCommonAncestorofaBinarySearchTree235 {

    /**
     * 分为四种情况：
     * 1、p和q在root的同一侧（左侧）
     * 2、p和q在root的同一侧（右侧
     * 3、p和q分别在root的两侧
     * 4、p或q就是root
     * 对于第3、4种情况来说，最小公共父节点就是root。
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;
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
