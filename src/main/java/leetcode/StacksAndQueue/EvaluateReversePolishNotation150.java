package leetcode.StacksAndQueue;

import java.util.Stack;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/evaluate-reverse-polish-notation/
 * @date: 2020/3/25 10:08
 */
public class EvaluateReversePolishNotation150 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            // + - * /
            String s = tokens[i];
            if (!(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))) {
                stack.push(Integer.parseInt(s));
            } else {
                int n1 = stack.pop();
                int n2 = stack.pop();
                if (s.equals("+")) {
                    stack.push(n1 + n2);
                } else if (s.equals("-")) {
                    stack.push(n2 - n1);
                } else if (s.equals("*")) {
                    stack.push(n1 * n2);
                } else {
                    stack.push(n2 / n1);
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String[] ss = new String[]{"2", "1", "+", "3", "*"};
        System.out.println(new EvaluateReversePolishNotation150().evalRPN(ss));
        ss = new String[]{"4", "13", "5", "/", "+"};
        System.out.println(new EvaluateReversePolishNotation150().evalRPN(ss));
        ss = new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(new EvaluateReversePolishNotation150().evalRPN(ss));

    }
}
