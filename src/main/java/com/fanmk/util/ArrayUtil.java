package com.fanmk.util;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 21:05
 */
public class ArrayUtil {

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
