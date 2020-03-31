package leetcode.stack;

import java.util.Stack;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/valid-parentheses/
 * @date: 2020/3/25 9:54
 */
public class ValidParentheses20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.size() == 0) {
                    return false;
                }

                char match;
                if (c == ')') {
                    match = '(';
                } else if (c == ']') {
                    match = '[';
                } else {
                    match = '{';
                }

                if (stack.pop() != match) {
                    return false;
                }
            }

        }

        return stack.size() == 0;
    }

    public static void main(String[] args) {
        String s = "[()]}";
        System.out.println(new ValidParentheses20().isValid(s));
    }
}
