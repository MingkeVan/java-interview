package com.fanmk.sort;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 22:44
 */
public class MergeSort {


    public static void sort(int[] arr, int left, int right) {

        //此处可以优化 终止条件可以改为
        //if(right-left < 15)
        //调用insertionSort(arr,left,right);
        if (left >= right) {
            return;
        }

        int m = left + ((right - left) >> 1);
        sort(arr, left, m);
        sort(arr, m + 1, right);

        //此处可以进行优化
        //if(arr[m] > arr[m+1]) 才需要进行merge操作
        merge(arr, left, m, right);
    }

    // 对[left...mid] 和 [mid+1...right]进行merge
    public static void merge(int[] arr, int left, int mid, int right) {
        int[] tmpArr = new int[right - left + 1];

        for (int i = left; i <= right; i++) {
            tmpArr[i - left] = arr[i];
        }
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = tmpArr[j - left];
                j++;
            } else if (j > right) {
                arr[k] = tmpArr[i - left];
                i++;
            } else if (tmpArr[i - left] < tmpArr[j - left]) {
                arr[k] = tmpArr[i - left];
                i++;
            } else {
                arr[k] = tmpArr[j - left];
                j++;
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        MergeSort.sort(arr, 0, arr.length - 1);
        for (int anArr : arr) {
            System.out.print(anArr + " ");
        }
        System.out.println();

    }

}
