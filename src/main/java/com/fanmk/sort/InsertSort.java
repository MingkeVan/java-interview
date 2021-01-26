package com.fanmk.sort;

import static com.fanmk.util.ArrayUtil.swap;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 21:14
 */
public class InsertSort {

    public static void sort(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) { //每个元素跟他前一个元素比较
                    swap(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    public static void sortOptimization(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int tmp = arr[i];

            // j的终止位置即为插入位置
            int j;
            for (j = i; j > 0; j--) {
                if (tmp < arr[j - 1]) { //每个元素跟他前一个元素比较
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
            }
            arr[j] = tmp;

        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        int[] arr1 = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        InsertSort.sort(arr);
        InsertSort.sortOptimization(arr1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.println();
    }

}
