package googlefoobar;

public class Solution {
    static String[] convert = new String[] {
            "100000",
            "110000",
            "100100",
            "100110",
            "100010",
            "110100",
            "110110",
            "110010",
            "010100",
            "010110",
            "101000",
            "111000",
            "101100",
            "101110",
            "101010",
            "111100",
            "111110",
            "111010",
            "011100",
            "011110",
            "101001",
            "111001",
            "010111",
            "101101",
            "101111",
            "101011"
    };

    public static String solution(String s) {
        // Your code here
        if(s == null) {
            return "000000";
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i< s.length();i ++) {
            char c = s.charAt(i);
            if(c == ' ') {
                sb.append("000000");
            }else {
                int index = c- 'a';
                if(index <0) {
                    sb.append("000001");
                    index = c- 'A';
                }

                sb.append(convert[index]);
            }

        }
        return sb.toString();
    }
}
