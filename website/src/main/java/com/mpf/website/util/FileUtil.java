package com.mpf.website.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileUtil {

    public static void appendToFile(String data, String filePath) {
        PrintStream ps = null;
        try {
            int index = filePath.lastIndexOf(File.separator);
            String folderPath = filePath.substring(0, index);
//            String fileName = filePath.substring(index);
            File parentFolder = new File(folderPath);
            if (!parentFolder.exists()) {
                parentFolder.mkdirs();
            }
            File file = new File(filePath);
            ps = new PrintStream(new FileOutputStream(file));
            ps.println(data);
//            ps.append(data);
        } catch (Exception e) {
            log.info("FileUtil error, message:{}, e:{}", e.getMessage(), e);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public static String readFile(String filePath) {
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line;
            while ((line = br.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } catch (IOException e) {
            log.info("FileUtil error, message:{}, e:{}", e.getMessage(), e);
        }
        return sb.toString();
    }
}
