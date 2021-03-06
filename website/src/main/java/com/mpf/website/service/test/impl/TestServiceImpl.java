package com.mpf.website.service.test.impl;

import com.mpf.website.service.test.TestService;
import com.mpf.website.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @Author: MiaoPengfei
 * @Date: 2021/1/11 16:09
 * @Description:
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    public String test() {

        String urlStr = "http://m.wesane.com";
        String str = "";
        try {
            URL url = new URL(urlStr);
            URLConnection URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                log.info("success");
                InputStream in = httpConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bufr = new BufferedReader(isr);
                while ((str = bufr.readLine()) != null) {
                    System.out.println(str);
                }
                bufr.close();
            } else {
                log.error("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
        //拼接文件名
        String timeStr = DateUtil.dateTime2String(new Date(), "yyyyMMddHHmmss");
        String filename = file.getOriginalFilename();
        if (filename != null && filename.contains(".")) {
            filename = filename.split("\\.")[0];
        }
        filename += "_" + timeStr + ("." + file.getContentType().split("/")[1]);
        String localPath = System.getProperty("user.dir")
                + File.separator + "upload"
                + File.separator + "image";
        File tempFileParentFolder = new File(localPath);
        if (!tempFileParentFolder.exists()) {
            tempFileParentFolder.mkdirs();
        }
        String filepath = localPath + File.separator + filename;
        try {
            file.transferTo(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "image/" + filename;
    }

}
