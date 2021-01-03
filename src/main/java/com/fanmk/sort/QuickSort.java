package com.fanmk.sort;

import static com.fanmk.util.ArrayUtil.swap;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 21:03
 */
public class QuickSort {

    public static int findKthLargest(int[] nums, int k) {
        int low = 0;
        int high = nums.length - 1;
        int index = nums.length - k;
        while (low < high) {
            int p = partition(nums, low, high);
            if (p == index) {
                return nums[p];
            } else if (p > index) {
                high = p - 1;
            } else {
                low = p + 1;
            }
        }

        return low;
    }

    public static void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int p = partition2(arr, l, r);
        sort(arr, l, p - 1);
        sort(arr, p + 1, r);
    }


    /**
     * 双路快排，利用两个指针，划分条件为<和>=，容易出现不平衡。
     * 如果待排序数组中存在大量重复元素，性能容易退化到O(n^2)
     *
     * @param arr
     * @param l
     * @param r
     * @return
     */
    //返回p，使得arr[l...p-1] < arr[p] arr[p+1...r] > arr[p]
    public static int partition(int[] arr, int l, int r) {
        int pivot = arr[l];

        // arr[l+1...j] < pivot arr[j+1...i) >= pivot
        // i是当前遍历的节点
        // 初始化J和i的值使得 上述区间为空
        int j = l;
        int i = l + 1;
        while (i <= r) {
            if (arr[i] < pivot) {
                //swap(arr[j+1],arr[i])
                swap(arr, j + 1, i);
                j++;
                i++;
            } else {
                i++;
            }

        }

        //swap(arr[j],arr[l])
        arr[l] = arr[j];
        arr[j] = pivot;
        return j;
    }

    /**
     * 双路快排优化版
     * 解决划分不平衡的问题，将=基准的元素平分到两边
     *
     * @param arr
     * @param l
     * @param r
     * @return
     */
    public static int partition1(int[] arr, int l, int r) {
        int pivot = arr[l];
        while (l < r) {
            while (l < r && arr[r] >= pivot) r--;
            if (l < r)
                arr[l++] = arr[r];
            while (l < r && arr[l] <= pivot) l++;
            if (l < r)
                arr[r--] = arr[l];
        }

        arr[l] = pivot;
        return l;
    }

    /**
     * 双路快排优化版2
     * 解决划分不平衡的问题，将=基准的元素平分到两边
     *
     * @param arr
     * @param l
     * @param r
     * @return
     */
    public static int partition2(int[] arr, int l, int r) {
        int pivot = arr[l];
        int i = l + 1; //arr[l+1...i) <= pivot
        int j = r; //arr(j...r] <= pivot
        while (true) {
            while (i <= r && arr[i] <= pivot) i++;
            while (j >= l + 1 && arr[j] >= pivot) j--;
            if (i > j) {
                break;
            }
            swap(arr, i, j);
            j--;
            i++;
        }

        swap(arr, l, j); //l与j交换 ,因为i可能越界
        return j;
    }

    /**
     * 三路快排，利用三个指针
     *
     * @param arr
     * @param l
     * @param r
     */
    public static void quickSortThreeWays(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int pivot = arr[l];
        int lt = l; // arr[l+1...lt] < pivot lt:less than
        int gt = r + 1; // arr[gt...r] > pivot gt:greater than
        int i = l + 1; // arr[lt+1...i) == pivot

        while (i < gt) {
            if (arr[i] < pivot) {
                swap(arr, lt + 1, i); //交换元素，使得arr[lt+1] < pivot
                lt++; //lt指针右移
                i++; //当前指针右移
            } else if (arr[i] > pivot) {
                swap(arr, gt - 1, i); //交换元素，使得arr[gt-1] > pivot
                gt--; //gt指针左移,i不动
            } else {
                i++; //当前指针右移
            }
        }

        swap(arr, l, lt);
        lt--;
        // arr[lt+1...gt-1] == pivot
        quickSortThreeWays(arr, l, lt);
        quickSortThreeWays(arr, gt, r);
    }

    /**
     * 三路快排，利用三个指针
     *
     * @param arr
     * @param l
     * @param r
     * @implSpec 三路快排优化版 代码可读性更强
     */
    public static void quickSortThreeWays1(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int pivot = arr[l];
        int lt = l; //arr[l..lt) < pivot
        int gt = r; //arr(gt,r] > pivot

        int i = l + 1; //arr[lt...i) == pivot

        while (i <= gt) {
            if (arr[i] == pivot) {
                i++;
            } else if (arr[i] < pivot) {
                swap(arr, lt++, i++);
            } else {
                swap(arr, gt--, i);
            }
        }


        // arr[lt...gt] == pivot
        quickSortThreeWays1(arr, l, lt - 1);
        quickSortThreeWays1(arr, gt + 1, r);
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        QuickSort.sort(arr, 0, arr.length - 1);
        printArr(arr);

        int[] arr1 = new int[]{2, 3, 4, 3, 7, 2, 5, 9, 7};
        QuickSort.quickSortThreeWays1(arr1, 0, arr1.length - 1);
        printArr(arr1);

        int index = QuickSort.findKthLargest(arr, 4);
        System.out.println(index);
    }
}
