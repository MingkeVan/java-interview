package com.fanmk.LinkedList;

import static com.fanmk.LinkedList.LinkedList.printLinkedList;

/**
 * @program: com.fanmk.interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://www.nowcoder.com/practice/fc533c45b73a41b0b44ccba763f866ef?tpId=13&tqId=11209&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 * @date: 2020/2/29 22:43
 */
public class DeleteDuplicationSolution {
    public ListNode deleteDuplication(ListNode pHead) {

        ListNode Head = new ListNode(-1); //创建头结点
        Head.next = pHead;
        ListNode pre = Head;
        ListNode tmp = Head.next;

        while (tmp != null) {
            if(tmp.next != null && tmp.next.val == tmp.val) {
                while (tmp.next != null && tmp.next.val == tmp.val) {
                    tmp = tmp.next;
                }

                pre.next = tmp.next; //删除重复节点
                tmp = tmp.next;
            }
            else {
                pre = pre.next;
                tmp = tmp.next;
            }
        }

        return Head.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode tmpHead = head;
        head.next = new ListNode(2);
        head = head.next;
        head.next = new ListNode(3);
        head = head.next;
        head.next = new ListNode(3);
        head = head.next;
        head.next = new ListNode(4);
        head = head.next;
        head.next = new ListNode(4);
        head = head.next;
        head.next = new ListNode(5);

        ListNode res = new DeleteDuplicationSolution().deleteDuplication(tmpHead);
        printLinkedList(res);
    }
}
