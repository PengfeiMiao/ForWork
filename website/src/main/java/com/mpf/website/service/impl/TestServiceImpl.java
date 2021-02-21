package com.mpf.website.service.impl;

import com.mpf.website.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
}
