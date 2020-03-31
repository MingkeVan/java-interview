package sort;

import static util.ArrayUtil.swap;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 20:56
 */
public class SelectionSort {

    public static void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            swap(arr, minIndex, i);
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        SelectionSort.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
