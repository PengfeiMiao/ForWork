package com.mpf.website.util;

import java.io.*;

public class FileUtil {
    public void createStrToFile(String filePath) {
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println("https://www.jb51.net");// 往文件里写入字符串
            ps.append("https://www.jb51.net");// 在已有的基础上添加字符串
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void appendStrToFile(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("在已有的基础上添加字符串");
            bw.write("abc\r\n ");// 往已有的文件上添加字符串
            bw.write("def\r\n ");
            bw.write("hijk ");
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
