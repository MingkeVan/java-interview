package sort;

/**
 * @author comeandgo2014@gmail.com
 * @decription https://leetcode-cn.com/problems/sort-list/comments/
 * @date 2020/2/29 15:39
 *
 * 链表归并排序
 */
public class SortLinkedList {

    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    /**
     * 递归调用mergeSort
     *
     * @param head
     * @return
     */
    private ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = findMiddleNode(head);
        ListNode l1 = mergeSort(head);
        ListNode l2 = mergeSort(mid);
        return merge(l1, l2);
    }

    /**
     * 合并两个有序链表
     *
     * @param l1
     * @param l2
     * @return
     */
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode tmp = new ListNode(-1);
        ListNode head = tmp;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tmp.next = l1;
                l1 = l1.next;
            } else {
                tmp.next = l2;
                l2 = l2.next;
            }
            tmp = tmp.next;
        }

        tmp.next = l1 == null ? l2 : l1;

        return head.next;
    }

    /**
     * 快慢指针找中点，并在中点处截断原链表
     *
     * @param head
     * @return
     */
    private ListNode findMiddleNode(ListNode head) {
        ListNode p = head;
        ListNode q = head;
        ListNode pre = head;
        //快指针为null的时候停止，此时慢指针在中点位置
        while (q != null && q.next != null) {
            pre = p;
            p = p.next;
            q = q.next.next;
        }

        pre.next = null; //截断为两部门 一部分的头为head 一部门的头为p
        return p;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        ListNode tmpHead = head;
        head.next = new ListNode(2);
        head = head.next;
        head.next = new ListNode(4);
        head = head.next;
        head.next = new ListNode(1);
        head = head.next;
        head.next = new ListNode(7);
        head = head.next;
        head.next = new ListNode(6);
        head = head.next;

        ListNode newHead = new SortLinkedList().mergeSort(tmpHead);

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
