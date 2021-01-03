package com.fanmk.LinkedList;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription:
 * @date: 2020/2/29 16:40
 */
public class LinkedList {

    //链表反转【递归版本】
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode tail = reverseList(head.next); //递归找到尾结点，最后返回作为头结点
        head.next.next = head;
        head.next = null;
        return tail;
    }

    //链表反转【迭代版本】
    public ListNode reverseList1(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;

        while (cur != null) {
            ListNode next = cur.next; //记录下一个节点
            cur.next = pre; //当前节点指针指向前一个节点
            pre = cur; //pre指向当前节点
            cur = next; //当前节点后移
        }

        return pre;
    }

    //链表部分反转【递归版本】
    public ListNode reverseBetween(ListNode head, int m, int n) {
        int i = 1;
        ListNode tmp = head;
        ListNode pre = null; //位置为m的节点的前置节点
        // 寻找位置为m节点 并记录其前置节点
        while (m != i) {
            pre = tmp;
            tmp = tmp.next;
            i++;
        }
        ListNode phead = tmp;

        // 寻找位置为m节点 并记录其后置节点
        while (n != i) {
            tmp = tmp.next;
            i++;
        }

        ListNode post = tmp.next; //node为n的节点的后置节点
        tmp.next = null; //截断链表

        //反转m和n之间的链表
        phead = reverseList(phead);

        ListNode tmpHead = phead;
        //寻找反转后链表的尾结点
        while (tmpHead.next != null) {
            tmpHead = tmpHead.next;
        }

        //尾结点拼接上n之后的节点
        tmpHead.next = post;
        //m之前的节点拼接之后的新链表

        if (pre == null) {
            return phead;
        }

        pre.next = phead;
        return head;
    }

    //链表部分反转【迭代版本】 利用头插法的这种方法看不懂
    public ListNode reverseBetween1(ListNode head, int m, int n) {

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;

        for (int i = 1; i < m; i++) {
            pre = pre.next;
        }

        head = pre.next;

        for (int i = m; i < n; i++) {
            ListNode next = head.next; //每次都将head的next节点移动到首部
            head.next = next.next;
            next.next = pre.next;
            pre.next = next;

        }

        return dummy.next;

    }


    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        ListNode tmpHead = head;
//        head.next = new ListNode(5);
//        head = head.next;
//        head.next = new ListNode(7);


        ListNode newHead = new LinkedList().reverseBetween1(tmpHead, 1, 1);
        printLinkedList(newHead);
    }

    public static void printLinkedList(ListNode newHead) {
        while (newHead != null) {
            System.out.print(newHead.val + " ");
            newHead = newHead.next;
        }
        System.out.println();

    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
