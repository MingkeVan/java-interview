package sort;

import static util.ArrayUtil.swap;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 22:07
 */
public class BubbleSort {

    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    public static void sort1(int[] arr) {
        int n = arr.length;
        boolean swapped;
        int i = 0;
        do {
            swapped = false;
            for (int j = n - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    swapped = true;
                }
            }
            i++;
        } while (swapped);
    }

    // 优化： 记录每一轮循环后最后插入位置i;此时i以前是有序的。
    public static void sort2(int[] arr) {
        int n = arr.length;
        int i = 0;
        int pos;
        do {
            pos = n; //***pos前的数据都是有序的 所以需要初始化为n
            for (int j = n - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    pos = j;
                }
            }
            i = pos; //记录本轮循环 最后停止的交换位置，此前为有序
        } while (pos < n);
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        BubbleSort.sort2(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }
}
