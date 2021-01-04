package com.fanmk.interview.ali;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class FileHelper {

    /**
     * 将传入字符串重复指定次数，模拟大文件
     * @param str
     * @param count
     * @param filePath
     * @throws IOException
     */
    public static void generateBigFile(String str, long count,String filePath) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < count; i++) {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

}
