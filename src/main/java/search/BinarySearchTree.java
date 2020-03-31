package search;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: 二叉搜索树实现
 * @date: 2020/2/29 16:28
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private int count;
    private TreeNode root;

    public BinarySearchTree() {
        count = 0;
        root = null;
    }

    public void insert(Key key, Value val) {
        root = insert(root, key, val);
    }

    public TreeNode insert(TreeNode node, Key key, Value val) {
        if (node == null) {
            count++;
            return new TreeNode(key, val);
        }
        if (node.key.compareTo(key) == 0) {
            node.val = val;
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, val);
        } else {
            node.right = insert(node.right, key, val);
        }

        return node;
    }

    public boolean search(TreeNode root, Key x) {
        if (root == null) {
            return false;
        }
        if (root.key.compareTo(x) == 0) {
            return true;
        } else if (x.compareTo(root.key) < 0) {
            return search(root.left, x);
        } else {
            return search(root.right, x);
        }
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void inOrder() {
        inOrder(this.root);
    }

    public void preOrder() {
        preOrder(this.root);
    }

    public void postOrder() {
        postOrder(this.root);
    }


    public void preOrder(TreeNode root) {

        if (root == null) {
            return;
        }

        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);

    }



    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);
        System.out.println(root.val + " ");
        inOrder(root.right);
    }

    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val + " ");
    }


    public void levelOrder() {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.println(node.val + " ");
            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
        }
    }


    public static void main(String[] args) {
        BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>();
        tree.insert("b", 2);
        tree.insert("a", 1);
        tree.insert("c", 3);
        tree.insert("d", 4);

        tree.preOrder();
        System.out.println();
        tree.inOrder();
        System.out.println();
        tree.postOrder();
        System.out.println();
        System.out.println(tree.size());

    }

    private class TreeNode {
        Key key;
        Value val;
        private TreeNode left;
        private TreeNode right;

        TreeNode(Key k, Value v) {
            key = k;
            val = v;
        }
    }
}



