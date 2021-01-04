package com.fanmk.interview.ali;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

import static com.fanmk.interview.ali.FileHelper.generateBigFile;

/**
 * 统计一篇文章中每个单词出现的次数（文章大小超过10G），
 * 例如输入：
 * If not now when , if not me who?
 * 输出
 * if 2
 * not 2
 * now 1
 * when 1
 * me 1
 * who 1
 * 备注：文章已做过预处理，已将所有标点符号替换为空格。用多线程处理。
 * <p>
 * 思路：
 * 1. 主线程通过FileChannel读取文件，将文件拆分为指定数目的子任务
 * 2. 主线程构建全局map, 利用concurrentHashMap和LongAdder进行多线程下的单词计数
 * 3. 由5个线程的线程池执行单词计数的子任务
 * 3.1 子线程将传入的字符串按空格拆分，并计数
 * 3.2 子线程完成计数后，countDownLatch.countDown()
 */
public class WordCount {

    static CountDownLatch countDownLatch;
    static Executor executor;
    static Map<String, LongAdder> res;

    public static Map<String, LongAdder> countWords(String filePath, Integer splitCount) throws InterruptedException, IOException {

        //1. 构造线程数为5的线程池
        int threadCount = 5;
        executor = Executors.newFixedThreadPool(threadCount);

        //2. 声明返回值map
        res = new ConcurrentHashMap<>();

        // 3.将文件计数拆分为若干个任务，通过CountDownLatch计数
        int taskCount = splitCount == null ? 10000 : splitCount;
        countDownLatch = new CountDownLatch(taskCount);

        //4. 分割文件
        splitFile(filePath, taskCount);
        countDownLatch.await();

        return res;
    }

    /**
     * 将指定文件拆分为指定数目的小文件
     *
     * @param filePath
     * @return 分割的文件路径列表
     */
    public static void splitFile(String filePath, int fileCount) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        long average = fileSize / fileCount;//平均值
        long bufferSize = 200; //缓存块大小，自行调整

        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + "")); // 申请一个缓存区

        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (int i = 0; i < fileCount; i++) {
            outputStream.reset();

            // 将上一次剩余的字节放入本次字节流
            if (tmpOutputStream.size() != 0) {
                tmpOutputStream.writeTo(outputStream);
                tmpOutputStream.reset();
            }

            if (i + 1 != fileCount) {
                int read = inputChannel.read(byteBuffer);// 读取数据
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();

                    if (outputStream.size() + bufferSize > average) {
                        for (int j = read - 1; j >= 0; j--) {
                            byte b = array[j];

                            // 判断空格 换行等 避免截断
                            if (b == 10 || b == 13 || b == 32) {

                                //将前半部分数组，写入输出字节流
                                outputStream.write(array, 0, j);
                                // 将后半部分数组，写入缓存字节流
                                tmpOutputStream.write(array, j, read - j);

                                break readW;
                            }
                        }
                    }

                    // 将整个字节数组写入字节流
                    outputStream.write(array);

                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer);
                }
            } else {
                int read = inputChannel.read(byteBuffer);// 读取数据

                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();

                    // 将整个字节数组写入字节流
                    outputStream.write(array);

                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer);
                }

            }

            if (outputStream.size() != 0) {
                executor.execute(new CountWordThread(
                        countDownLatch,
                        i,
                        outputStream.toString(),
                        res)
                );

            }
        }

        inputChannel.close();
        fis.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 构造大文件
        int lineCnt = 1000 * 1000 * 1000;
//
//        long start = System.currentTimeMillis();
//        generateBigFile("If not now when if not me who", lineCnt, "./big_file_10G.txt");
//        long end = System.currentTimeMillis();
//        System.out.println("生成大文件耗时：" + (end - start));

        // 用例1: 小文件
        long start = System.currentTimeMillis();
        Map<String, LongAdder> res = countWords("./small_file.txt", 1);
        long end = System.currentTimeMillis();
        System.out.println("单词计数耗时：" + (end - start));

        assert res.get("now").longValue() == 1;

        long start1 = System.currentTimeMillis();
        System.out.println("开始多线程单词计数");

        Map<String, LongAdder> res1 = countWords("./big_file_10G.txt", 10000);

        long end1 = System.currentTimeMillis();
        System.out.println("结束多线程单词计数");
        System.out.println("多线程单词计数耗时：" + (end1 - start1));

        assert res1.get("now").longValue() == lineCnt;
    }

}
