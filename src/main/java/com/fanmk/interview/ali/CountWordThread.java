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

    private FileChannel fileChannel = null;
    private FileLock lock = null;
    private MappedByteBuffer mbBuf = null;

    private CountDownLatch countDownLatch;
    private int index;
    //    private String filePath;
    private File file;
    private Map<String, LongAdder> wordMap;


    /**
     * 构造执行计算单词数量的线程
     * 1.对传入的文件进行单词的计数；
     * 2.将计数结果添加到map中
     * 3. countdownLatch - 1
     *
     * @param countDownLatch
     * @param file
     * @param wordMap
     */
    public CountWordThread(CountDownLatch countDownLatch,
                           int index,
                           String str,
                           Map<String, LongAdder> wordMap) {
        this.countDownLatch = countDownLatch;
        this.index = index;
//        this.filePath = filePath;
        this.file = file;
        this.wordMap = wordMap;

        try {
            // 得到当前文件的通道
            fileChannel = new RandomAccessFile(file, "rw").getChannel();
            // 锁定当前文件的部分
            lock = fileChannel.lock(start, size, false);
            // 对当前文件片段建立内存映射
            mbBuf = fileChannel.map(FileChannel.MapMode.READ_ONLY, start, size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(filePath);
//        } catch (FileNotFoundException e) {
//            System.out.println("文件未找到");
//            return;
//        }
//
//        FileChannel inputChannel = fis.getChannel();
//        long bufferSize = 20000000; //缓存块大小，设定为20m=20000k = 20 000 000 b
//        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + "")); // 申请一个缓存区
//        long position = 0;//游标
//
//        int index = 0;
//        int read;// 读取数据
//        try {
//            read = inputChannel.read(byteBuffer, position);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        //死循环读取文件
//        while (read != -1) {
//            byteBuffer.flip();//切换读模式
//            byte[] array = byteBuffer.array();
//
//            //倒序遍历，查找换行符或空格字符，避免单词被拆分
//            for (int j = array.length - 1; j >= 0; j--) {
//                byte b = array[j];
//
//                //先判断换行符\n 再判断回车\r 再判断空格
//                if (b == 10 || b == 13|| b == 32) {
//                    position += j;
//                    break;
//                }
//            }
//
//            byteBuffer.clear(); //重置缓存块指针
//            try {
//                read = inputChannel.read(byteBuffer, position);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            inputChannel.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        File file = new File(filePath);
//        if (!file.exists()) {
//            System.out.println("文件不存在");
//            return;
//        }
//        Scanner scanner;
//        try {
//            scanner = new Scanner(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        while (scanner.hasNext()) {
//
//            String line = scanner.nextLine();
////            System.out.println(line);
//            //\w+ : 匹配所有的单词
//            //\W+ : 匹配所有非单词
//            String[] lineWords = line.split("\\W+");
//
//            for (String word : lineWords) {
//                LongAdder adder = wordMap.computeIfAbsent(word, (key) -> new LongAdder());
//                adder.increment();
//            }
//
//        }

        String str = Charset.forName("UTF-8").decode(mbBuf).toString();
        String[] arr = str.split("[^a-zA-Z']+");
        for (String word : arr) {
            // 转成小写，通过原子类实现多线程写map
            LongAdder adder = wordMap.computeIfAbsent(word.toLowerCase(), (key) -> new LongAdder());
            adder.increment();
        }
        try {
            // 释放文件锁
            lock.release();
            // 关闭文件通道
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
        System.out.println("第" + this.index + "个任务完成");
    }

    public static void main(String[] args) throws InterruptedException {
        Map<String, LongAdder> map = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new CountWordThread(countDownLatch, 0, new File("./small_file.txt"), 0, 200, map)).start();
        countDownLatch.await();
        System.out.println(map);
    }
}
