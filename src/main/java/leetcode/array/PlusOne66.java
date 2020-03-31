package leetcode.array;

/**
 * @program: interview
 * @author: fanmk
 * @email: comeandgo2014@gmail.com
 * @decription: https://leetcode.com/problems/plus-one/
 * @date: 2020/3/31 22:59
 */
public class PlusOne66 {
    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return null;
        }

        int add = 0;
        for (int i = digits.length - 1; i >= 0; i--) {

            if (i == digits.length - 1) {
                digits[i] = digits[i] + 1 + add;
            } else {
                digits[i] += add;
            }

            add = digits[i] / 10;
            digits[i] = digits[i] % 10;

            if (add == 0) {
                break;
            }

        }

        if (add == 0) {
            return digits;
        }

        int[] resint = new int[digits.length + 1];
        resint[0] = add;
        for (int i = 1; i < resint.length; i++) {
            resint[i] = digits[i - 1];
        }

        return resint;
    }
}
