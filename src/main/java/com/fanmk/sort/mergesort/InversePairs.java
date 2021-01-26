package com.fanmk.sort.mergesort;
import java.util.*;

public class InversePairs {
    class Solution {

        private int sum = 0;
        public int InversePairs(int [] array) {
            if(array == null || array.length ==0) {
                return 0;
            }

            // 归并排序变种 需要通过数组拷贝记录排序结果
            int[] copy = Arrays.copyOf(array,array.length);

            return mergesort(array,copy,0,array.length -1);
        }

        // 拆分为子数组 进行归并排序 同时统计子数组的逆序对；
        // 两个子数组合并时 需要进行二次计算
        private int mergesort(int[] array,int[] copy, int l , int r) {
            if(l >= r) {
                return 0;
            }

            int m = l + (r-l) / 2;
            int leftCount = mergesort(array,copy,l,m) % 1000000007;
            int rightCount = mergesort(array,copy,m+1,r) % 1000000007;

            int count = 0;

            //从两个子数组的最右侧进行合并
            int i = m;
            int j = r;
            int copyr = r;

            while( i >=l && j >m) {
                if(array[i] > array[j]) {
                    copy[copyr--] = array[i--];
                    count += (j-m);

                    if(count >= 1000000007) {
                        count %= 1000000007;
                    }
                }
                else {
                    copy[copyr--] = array[j--];

                }


            }

            while(i>=l) {
                copy[copyr--] = array[i--];
            }

            while( j >m) {
                copy[copyr--] = array[j--];
            }

            for(int s = l; s<= r; s++) {
                array[s] = copy[s];
            }

            return (leftCount + rightCount + count) % 1000000007;


        }
    }
}
