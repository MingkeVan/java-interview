package leetcode.binarytree;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 * @date: 2020/5/9 18:29
 */
public class ConvertSortedArraytoBinarySearchTree108 {

    /**
     * 将有序数组转为平衡二分搜索树
     *
     * <p>
     *     借鉴二分搜索的方法，将中间节点作为根节点，并分隔成两个子树
     * </p>
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return bst(nums, 0, nums.length - 1);
    }

    private TreeNode bst(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = bst(nums, start, mid - 1);
        root.right = bst(nums, mid + 1, end);
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
