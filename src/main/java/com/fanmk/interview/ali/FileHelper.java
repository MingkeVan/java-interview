package com.fanmk.interview.ali;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

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
 */
public class FileHelper {
    static class FileSplit {
        long start;
        long size;

        FileSplit(long start, long size) {
            this.start = start;
            this.size = size;
        }
    }

    public static void generateBigFile() throws IOException {
        File file = new File("./big_file_10G.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String str = "apple bad big append map many much pod pod bad this that where when append";
        for (int i = 0; i < 1000000000; i++) {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 将指定文件拆分为指定数目的小文件
     *
     * @param filePath
     * @return 分割的文件路径列表
     */
    public static List<FileSplit> splitFile(String filePath,
                                            int fileCount,
                                            CountDownLatch countDownLatch,
                                            Executor executor) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        long average = fileSize / fileCount;//平均值
        long bufferSize = 200; //缓存块大小，自行调整
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + "")); // 申请一个缓存区
        long startPosition = 0; //子文件开始位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();
                    for (int j = array.length - 1; j >= 0; j--) {
                        byte b = array[j];
                        if (b == 10 || b == 13 || b == 32) { //判断\n\r
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            } else {
                endPosition = fileSize; //最后一个文件直接指向文件末尾
            }

            byteBuffer.get();
            byte[] newarr = new byte[Integer.valueOf(endPosition - startPosition + "")];
            System.arraycopy();
            executor.execute(
                    new CountWordThread(
                            countDownLatch,
                            i,
                            new String(),
                            fileList.get(i).start,
                            fileList.get(i).size,
                            res)
            );

            startPosition = endPosition + 1;
            endPosition += average;
        }

        inputChannel.close();
        fis.close();
        return splitFileList;
    }


    public static void main(String[] args) throws IOException {

        generateBigFile();

//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
//        long startTime = System.currentTimeMillis();
//        List<String> list = splitFile("./small_file.txt");
//        long endTime = System.currentTimeMillis();
//        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
//        System.out.println(list.size());
//        scanner.nextLine();
    }

}
