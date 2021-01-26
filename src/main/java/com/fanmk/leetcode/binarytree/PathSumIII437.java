package com.fanmk.leetcode.binarytree;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/path-sum-iii/
 * @date: 2020/5/7 23:22
 */
public class PathSumIII437 {

    /**
     * 分别求出当前节点在路径上和不在路径上的总数，然后递归求解，时间复杂度较高
     * todo：另一种复杂度更低的解法
     * @param root
     * @param sum
     * @return
     */
    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        int res = 0;
        //包含当前节点的路径数
        res += findPath(root, sum);

        //不包含当前节点的路径数
        res += pathSum(root.left, sum);
        res += pathSum(root.right, sum);
        return res;
    }

    //寻找包含root的路径数
    private int findPath(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        int res = 0;
        if (root.val == sum) {
            res += 1; //因为可能存在负数，所以这里不return
        }

        res += findPath(root.left, sum - root.val);
        res += findPath(root.right, sum - root.val);
        return res;
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
