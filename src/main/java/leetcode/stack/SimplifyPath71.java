package leetcode.stack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/simplify-path/
 * @date: 2020/3/25 11:01
 */
public class SimplifyPath71 {
    public String simplifyPath(String path) {
        //使用双向链表代替栈
        Deque<String> stack = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (String dir : path.split("/")) {
            if (dir.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop(); //removeFirst
                }
            } else if (!(dir.isEmpty() || dir.equals("."))) {
                stack.push(dir); //addFirst
            }
        }


        if (stack.isEmpty()) {
            return "/";
        }

        StringBuilder res = new StringBuilder();

        while (!stack.isEmpty()) {
            res.append("/").append(stack.pollLast());
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String s = "/home/";
        System.out.println(new SimplifyPath71().simplifyPath(s));

        s = "/../";
        System.out.println(new SimplifyPath71().simplifyPath(s));

        s = "/home//foo/";
        System.out.println(new SimplifyPath71().simplifyPath(s));

        s = "/a/./b/../../c/";
        System.out.println(new SimplifyPath71().simplifyPath(s));

        s = "/a/../../b/../c//.//";
        System.out.println(new SimplifyPath71().simplifyPath(s));

        s = "/a//b////c/d//././/..";
        System.out.println(new SimplifyPath71().simplifyPath(s));

    }


}
