package sort;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/merge-k-sorted-lists
 * @date: 2020/4/12 16:39
 * <p>
 * k的有序链表合并为一个有序链表
 * 思路：采用divide-conquer的思想
 */
public class MergeKSortedLists {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length <= 0) {
            return null;
        }

        return sort(lists, 0, lists.length - 1);
    }

    private ListNode sort(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }

        int m = l + (r - l) / 2;
        return merge(sort(lists, l, m), sort(lists, m + 1, r));
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
        ListNode dummy = tmp;
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

        return dummy.next;
    }

}
