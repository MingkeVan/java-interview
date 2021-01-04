package com.fanmk.interview.ali;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

public class CountWordThread implements Runnable {

    private CountDownLatch countDownLatch;
    private int index;
    private String str;
    private Map<String, LongAdder> wordMap;


    /**
     * 构造执行计算单词数量的线程
     * 1.对传入的字符串进行单词的计数；
     * 2.将计数结果添加到map中
     * 3. countdownLatch - 1
     *
     * @param countDownLatch
     * @param index
     * @param str
     * @param wordMap
     */
    public CountWordThread(CountDownLatch countDownLatch,
                           int index,
                           String str,
                           Map<String, LongAdder> wordMap) {
        this.countDownLatch = countDownLatch;
        this.index = index;
        this.str = str;
        this.wordMap = wordMap;
    }

    @Override
    public void run() {
        String[] arr = this.str.split("[^a-zA-Z']+");
        for (String word : arr) {
            // 转成小写，通过原子类实现多线程写map
            if(!word.isEmpty()) {
                LongAdder adder = wordMap.computeIfAbsent(word.toLowerCase(), (key) -> new LongAdder());
                adder.increment();
            }

        }

        countDownLatch.countDown();
        System.out.println("第" + this.index + "个任务完成");
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String, LongAdder> map = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new CountWordThread(countDownLatch, 0, "If not now when if not me who", map)).start();
        countDownLatch.await();
        System.out.println(map);
    }
}
