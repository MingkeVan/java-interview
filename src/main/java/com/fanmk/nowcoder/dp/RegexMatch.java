package com.fanmk.nowcoder.dp;

/**
 * @author comeandgo2014@gmail.com
 * @decription https://www.nowcoder.com/practice/45327ae22b7b413ea21df13ee7d6429c?tpId=13&tags=&title=&diffculty=0&judgeStatus=0&rp=1
 * @date 2021/1/9 17:39
 */
public class RegexMatch {

    class Solution {

        // 分情况递归解决
        public boolean match(char[] str, char[] pattern)
        {
            return  matchStr(str,pattern,0,0);
        }

        // 子问题 字符串从i开始 模式从j开始 两者是否匹配
        private boolean matchStr(char[] str, char[] pattern,int i,int j) {

            //两个字符串都为空，返回true
            if(i>=str.length && j >= pattern.length) {
                return true;
            }

            //当第一个字符串不空，而第二个字符串空了，返回false（因为这样，就无法
            //    匹配成功了,而如果第一个字符串空了，第二个字符串非空，还是可能匹配成
            //    功的，比如第二个字符串是“a*a*a*a*”,由于‘*’之前的元素可以出现0次，
            //    所以有可能匹配成功）
            if(j>=pattern.length) {
                return false;
            }

            // 1.如果模式字符的下一个字符是*
            //   1.1 若当前字符与模式字符相同  或者 模式字符为.   则 取下面两种情况的并集
            //       1.1.1 贪婪匹配：当前字符后移一位 模式字符不动
            //       1.1.2 匹配*号后的字符： 当前字符不动，模式字符后移两位
            //   1.2 若当前字符与模式字符不同 并且 模式字符不为. 则 返回 false
            // 2.如果模式字符的下一个字符不是*
            //    若当前字符与模式字符相同  或者 模式字符为. 当前字符和模式字符都后移
            //    否则返回false

            //pattern下一个字符为‘*’时，稍微复杂一些，因为‘*’可以代表0个或多个。
            //   这里把这些情况都考虑到：
            //      a>当‘*’匹配0个字符时，str当前字符不变，pattern当前字符后移两位，
            //       跳过这个‘*’符号；
            //      b>当‘*’匹配1个或多个时，str当前字符移向下一个，pattern当前字符
            //      不变。（这里匹配1个或多个可以看成一种情况，因为：当匹配一个时，
            //       由于str移到了下一个字符，而pattern字符不变，就回到了上边的情况a；
            //       当匹配多于一个字符时，相当于从str的下一个字符继续开始匹配）
            if( j +1 < pattern.length && pattern[j+1] == '*') {
                if(i< str.length && (str[i] == pattern[j] || pattern[j] == '.')) {
                    return matchStr(str,pattern,i+1,j) || matchStr(str,pattern,i,j+2);
                }
                else {
                    return matchStr(str,pattern,i,j+2);
                }

            }
            // pattern下一个字符不为‘*’：这种情况比较简单，直接匹配当前字符。如果
            //    匹配成功，继续匹配下一个；如果匹配失败，直接返回false。注意这里的
            //   “匹配成功”，除了两个字符相同的情况外，还有一种情况，就是pattern的
            //   当前字符为‘.’,同时str的当前字符不为‘\0’。
            else {
                if(i< str.length && (str[i] == pattern[j] || pattern[j] == '.')) {
                    return matchStr(str,pattern,i+1,j+1);
                } else {
                    return false;
                }
            }

        }
    }
}
