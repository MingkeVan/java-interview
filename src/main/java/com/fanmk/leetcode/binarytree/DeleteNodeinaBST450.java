package com.fanmk.leetcode.binarytree;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://com.fanmk.leetcode.com/problems/delete-node-in-a-bst/
 * @date: 2020/5/9 17:18
 */
public class DeleteNodeinaBST450 {

    /**
     * 本方法为第一版代码，逻辑略复杂，请参考下方优化版代码 {@link #deleteNode1(TreeNode, int)}
     *
     * <p>本题目只要求删除符合条件的一个节点,并返回删除后的根节点
     * 分三种情况讨论：
     * 1、要删除的key是当前节点
     * 2、要删除的key在左子树
     * 3、要删除的key在右子树
     *
     * @param root
     * @param key
     * @return 删除后的根节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (root.val == key) {
            //找到后继节点，并替换当前根节点
            TreeNode successor = findSuccessor(root);
            if (successor != null) {
                root.val = successor.val;
                return root;
            }

            //无后继节点，则返回左子树的根节点
            return root.left;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
            return root;
        }

        root.right = deleteNode(root.right, key);
        return root;

    }

    /**
     * 寻找 {@code node}的后继节点并删除
     *
     * @param node
     * @return
     */
    private TreeNode findAndDeleteSuccessor(TreeNode node) {
        if (node == null) {
            return null;
        }

        if (node.right == null) {
            return null;
        }

        TreeNode pre = null;
        TreeNode tmp = node.right;

        while (tmp.left != null) {
            pre = tmp;
            tmp = tmp.left;
        }

        if (pre == null) {
            node.right = tmp.right;
        } else {
            pre.left = tmp.right;
        }

        return tmp;
    }

    /**
     * 优化版：精简代码
     * 本题目只要求删除符合条件的一个节点,并返回删除后的根节点
     *
     * <p>分三种情况讨论：
     * 1、要删除的key是当前节点
     * * 1.1、当前节点只有一个孩子
     * * 1.2、当前节点有两个孩子
     * 2、要删除的key在左子树
     * 3、要删除的key在右子树
     *
     * @param root
     * @param key
     * @return 删除后的根节点
     */
    public TreeNode deleteNode1(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode1(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode1(root.right, key);
        } else {
            // key == root.val
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }

            //找到后继节点（一定存在），并替换当前根节点
            TreeNode successor = findSuccessor(root);
            root.val = successor.val;

            //在右子树上删除后继节点
            root.right = deleteNode1(root.right, successor.val);
        }

        return root;
    }

    /**
     * 寻找当前根节点的后继节点
     * 当前根节点和右孩子不为空
     *
     * @param root
     * @return
     */
    private TreeNode findSuccessor(TreeNode root) {
        TreeNode tmp = root.right;
        while (tmp.left != null) {
            tmp = tmp.left;
        }

        return tmp;
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
