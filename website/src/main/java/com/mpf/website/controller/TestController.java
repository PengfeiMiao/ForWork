package com.mpf.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.service.test.TestService;
import com.mpf.website.staticobj.CommonStatic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;


/**
 * @author: MiaoPengfei
 * @date: 2021/2/15 10:44
 * @description:
 * @since: 1.0.20
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "测试接口", tags = "测试接口")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public RestResponse test() {
        RestResponse response = new RestResponse();

        try {
            String data = testService.test();
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(data);
        } catch (Exception e) {
            log.error("fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Value("${properties.proxy.server}")
    private String serverAddr;

    @ApiOperation(value = "上传图片", notes = "editormd图片上传")
    @PostMapping("/saveImage")
    @ResponseBody
    //接收图片的参数名需要为"editormd-image-file"
    public JSONObject saveImage(@ApiParam("图片文件") @RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        /** 从request中获取 */
//        file = ((MultipartHttpServletRequest) request).getFile("editormd-image-file");
        String path = testService.uploadFile(file);
        if (StringUtils.isBlank(path)) {
            jsonObject.put("success", 0);//图片上传失败的信息码
            jsonObject.put("message", "upload error!");//信息
        } else {
            if(StringUtils.isBlank(serverAddr)){
                serverAddr = request.getServerName()+ ":" + request.getServerPort() ;
            }
            String url = "http://" + serverAddr + "/" + CommonStatic.APPLICATION_NAME + "/" + path;
            jsonObject.put("url", url);//图片回显地址，即文件存放地址，应为虚拟路径
            jsonObject.put("success", 1);//图片上传成功的信息码
            jsonObject.put("message", "upload success!");//信息
        }
        return jsonObject;
    }

    public static void main(String[] args) {

        String appid = "wx0f1cff9bd733c6cb";
        String secret = "420bdd7afc5af077eb184a178d04fe33";

        StringBuilder sb = null;
        byte[] entitydata = null;
        String line = null;
        StringBuffer response = new StringBuffer();
        JSONObject json = null;
        String accesstoken = "";

        URL url = null;
        HttpURLConnection httpUrlConn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            sb = new StringBuilder();
            sb.append("grant_type=client_credential");
            sb.append("&appid=");
            sb.append(URLEncoder.encode(appid, "UTF-8"));
            sb.append("&secret=");
            sb.append(URLEncoder.encode(secret, "UTF-8"));
            String urlStr = String.format("https://api.weixin.qq.com/cgi-bin/token", new Object[0]);
            url = new URL(urlStr);
            httpUrlConn = (HttpURLConnection)url.openConnection();
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setConnectTimeout(3000);
            httpUrlConn.setReadTimeout(3000);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            entitydata = sb.toString().getBytes();
            httpUrlConn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
            outputStream = httpUrlConn.getOutputStream();
            outputStream.write(entitydata);
            outputStream.flush();
            if (httpUrlConn.getResponseCode() == 200) {
                inputStream = httpUrlConn.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                while ((line = bufferedReader.readLine()) != null)
                    response.append(line);
                log.info(response.toString());
                json = JSONObject.parseObject(response.toString());
                if (json.containsKey("access_token"))
                    accesstoken = json.getString("access_token");
            }
            log.info("token:{}", accesstoken);
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            accesstoken = "exception";
            log.info("token:{}", accesstoken);
        } finally {
            sb = null;
            entitydata = null;
            line = null;
            response = null;
            url = null;
            if (json != null) {
                json.clear();
                json = null;
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                    inputStreamReader = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (httpUrlConn != null) {
                    httpUrlConn.disconnect();
                    httpUrlConn = null;
                }
            } catch (Exception ex) {
                log.error(ex.toString(), ex);
            }
        }
    }

}
