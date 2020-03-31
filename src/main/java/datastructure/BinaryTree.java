package datastructure;

import search.BinarySearchTree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: 思路参考https://juejin.im/post/59e3fde451882578c20858a5#heading-3
 * @date: 2020/3/28 12:13
 */
public class BinaryTree {
    private int count;

    private TreeNode root;

    public BinaryTree() {
        count = 0;
        root = null;
    }

    public List<Integer> inOrderNonRecursive() {
        return inOrderNonRecursive(this.root);
    }

    public List<Integer> preOrderNonRecursive() {
        return preOrderNonRecursive(this.root);
    }

    public List<Integer> postOrderNonRecursive() {
        return postOrderNonRecursive(this.root);
    }


    public List<Integer> preOrderNonRecursive1(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            results.add(treeNode.val);

            //右孩子先入栈 左孩子后入栈
            if (treeNode.right != null)
                stack.push(treeNode.right);

            if (treeNode.left != null)
                stack.push(treeNode.left);
        }

        return results;
    }

    public List<Integer> preOrderNonRecursive(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                results.add(cur.val);
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            cur = cur.right;
        }

        return results;
    }

    public List<Integer> inOrderNonRecursive(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            results.add(cur.val);
            cur = cur.right;
        }

        return results;
    }

    //其他两种遍历也可以参考这个思路来做
    public List<Integer> postOrderNonRecursive(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        if (root == null) {
            return results;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        TreeNode last = null; //记录右子树是否被访问过

        while (cur != null || !stack.isEmpty()) {
            //对以cur为根的树，向左遍历到最底层
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.peek();
            //如果当前节点右子树为空或者当前节点右子树已经访问过
            if (cur.right == null || cur.right == last) {
                results.add(cur.val);
                stack.pop();
                last = cur; //记录上一个访问节点
                cur = null;
            } else {
                //访问右子树
                cur = cur.right;
            }
        }

        return results;

    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(null, null, 3);
        tree.root.left = new TreeNode(null, null, 2);
        tree.root.right = new TreeNode(null, null, 7);

        tree.root.left.left = new TreeNode(null, null, 4);
        tree.root.left.right = new TreeNode(null, null, 5);


        tree.root.right.left = new TreeNode(null, null, 7);
        tree.root.right.right = new TreeNode(null, null, 1);
        List<Integer> arr1 = tree.preOrderNonRecursive();
        System.out.println(arr1);
        List<Integer> arr2 = tree.inOrderNonRecursive();
        System.out.println(arr2);

        List<Integer> arr3 = tree.postOrderNonRecursive();
        System.out.println(arr3);
    }

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        TreeNode(TreeNode l, TreeNode r, int v) {
            this.left = l;
            this.right = r;
            this.val = v;
        }

    }
}
