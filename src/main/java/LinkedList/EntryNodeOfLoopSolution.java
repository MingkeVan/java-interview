package LinkedList;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://www.nowcoder.com/practice/253d2c59ec3e4bc68da16833f79a38e4?tpId=13&tqId=11208&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 * @date: 2020/2/29 23:28
 */
public class EntryNodeOfLoopSolution {
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {

                //重新定义一个慢指针从头结点出发，当两个慢指针再次相遇，结果必为环的入口结点
                ListNode slow2 = pHead;
                while (slow != slow2) {
                    slow2 = slow2.next;
                    slow = slow.next;
                }
                return slow;
            }

        }
        return null;
    }
}
