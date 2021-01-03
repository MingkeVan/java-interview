package com.fanmk;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author comeandgo2014@gmail.com
 * @decription
 * @date 2020/2/26 15:49
 */
public class Application {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        // ? extends T 和 ? super T的区别
        //上界通配符 和 下届通配符


        Map<Integer,String> orderedMap=new HashMap<>();
        orderedMap.put(1,"a");
        orderedMap.put(3,"a");
        orderedMap.put(2,"a");
        System.out.println("LinkedHashMap output: "+orderedMap.toString());
     }
}
