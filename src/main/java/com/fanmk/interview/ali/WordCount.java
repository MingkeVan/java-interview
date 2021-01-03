package com.fanmk.interview.ali;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

public class WordCount {

    public static Map<String, LongAdder> countWords(String filePath) throws InterruptedException, IOException {
        File file = new File(filePath);
        //1. 分割文件
        System.out.println("开始分割文件");
        long size = 20 * 1024 * 1034; // 拆分为20mb
        List<FileHelper.FileSplit> fileList = FileHelper.splitFile(filePath,size);
        System.out.println("完成分割文件，总数为" + fileList.size());

        //2. 构造线程数为5的线程池
        int threadCount = 5;
        Executor executor = Executors.newFixedThreadPool(threadCount);

        //3. 声明返回值map
        Map<String, LongAdder> res = new ConcurrentHashMap<>();

        // 通过CountDownLatch计数
        CountDownLatch countDownLatch = new CountDownLatch(fileList.size());
        for (int i = 0; i < fileList.size(); i++) {
            executor.execute(
                    new CountWordThread(
                            countDownLatch,
                            i,
                            file,
                            fileList.get(i).start,
                            fileList.get(i).size,
                            res)
            );
        }
        countDownLatch.await();

        return res;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Map<String, LongAdder> res = countWords("./big_file_10G.txt");
        System.out.println(res);
    }
}
