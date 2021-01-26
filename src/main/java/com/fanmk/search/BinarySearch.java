package com.fanmk.search;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 15:58
 */
public class BinarySearch {

    /**
     * 二分搜索数组中的target元素
     * 不存在则返回-1
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int m = l + ((r - l) >> 1); //右移运算符优先级低
            if (nums[m] == target) {
                return m;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }

        }

        return -1;
    }

    /**
     * 寻找数组中的target对应的第一个索引
     * 如果不存在，寻找比target小的元素中的最大值对应的所有索引
     * 如果floor不存在，返回-1
     *
     * @param arr
     * @param target
     * @return
     */
    public int floor(int[] arr, int target) {

        int l = -1;
        int r = arr.length - 1;
        // 在(l...r]的范围进行查找
        while (l < r) {
            int m = l + (r + 1 - l) / 2; //+1向上取整，保持循环不变量，避免死循环
//            int m = l + (r - l) / 2;
            if (target <= arr[m]) {
                r = m - 1;
            } else {
                l = m;
            }
        }

        //此时l==r
        assert l == r;

        //arr[l+1] 有两种情况 大于或者等于target
        if (l + 1 < arr.length && arr[l + 1] == target) {
            return l + 1;
        }

        //arr[l]为最接近target的值 等于或者小于
        return l;
    }

    // 二分查找法, 在有序数组arr中, 查找target
    // 如果找到target, 返回最后一个target相应的索引index
    // 如果没有找到target, 返回比target大的最小值相应的索引, 如果这个最小值有多个, 返回最小的索引
    // 如果这个target比整个数组的最大元素值还要大, 则不存在这个target的ceil值, 返回整个数组元素个数n
    public int ceil(int[] arr, int target) {
        int l = 0;
        int r = arr.length;

        // [l,r)的区间中循环
        while (l < r) {
            int m = l + (r - l) / 2; //普通向下取整 保持循环不变量
            if (target >= m) {
                l = m + 1;
            } else {
                r = m;
            }
        }

        assert l == r;

        //arr[r-1] <= target
        if (r - 1 >= 0 && arr[r - 1] == target) {
            return r - 1;
        }

        //arr[r]为最接近target的值 等于或者大于
        return r;
    }

}
