package leetcode.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/restore-ip-addresses/
 * <p>
 * Given a string containing only digits, restore it by returning all possible valid IP address combinations.
 * <p>
 * A valid IP address consists of exactly four integers (each integer is between 0 and 255) separated by single points.
 * <p>
 * Example:
 * <p>
 * Input: "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 * @date: 2020/6/21 22:14
 */
public class RestoreIPAddresses93 {

    public List<String> restoreIpAddresses(String s) {

        List<String> res = new ArrayList<>();
        dfs(s, 0, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(String s, int index, int level, StringBuilder sb, List<String> res) {

        if (index >= s.length()) {
            if (level == 4)
                res.add(sb.deleteCharAt(sb.length() - 1).toString());
            return;
        }
        if (level >= 4) {
            return;
        }

        if (s.charAt(index) == '0') {
            //不需要重设sb的长度
            dfs(s, index + 1, level + 1, sb.append("0."), res);
        } else {

            int max = Integer.min(index + 3, s.length());
            for (int i = index; i < max; i++) {
                String cur = s.substring(index, i + 1);
                int tmp = Integer.parseInt(cur);
                if (tmp >= 0 && tmp <= 255) {
                    int length = sb.length();
                    dfs(s, i + 1, level + 1, sb.append(cur).append("."), res);
                    //需要重设sb的长度
                    sb.setLength(length);
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(Integer.parseInt("00"));
    }

}
