package leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * <p>
 * 本题通过回溯法
 * @date: 2020/6/21 17:47
 */
public class LetterCombinationsofaPhoneNumber17 {


    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();

        if ("".equals(digits)) {
            return res;
        }

        findCombinations(digits, 0, new StringBuilder(), res);

        return res;
    }


    private void findCombinations(String digits, int index, StringBuilder sb, List<String> res) {
        if (index == digits.length()) {
            res.add(sb.toString());
            return;
        }

        char c = digits.charAt(index);
        String letter = letters[c - '0'];
        for (int i = 0; i < letter.length(); i++) {
            int length = sb.length();
            findCombinations(digits, index + 1, sb.append(letter.charAt(i)), res);

            //回溯法需保证返回时引用变量恢复原样
            sb.setLength(length);
        }

    }

    private String[] letters = new String[]{
            " ",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };
}
