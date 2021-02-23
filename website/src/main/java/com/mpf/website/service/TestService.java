package com.mpf.website.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: MiaoPengfei
 * @Date: 2021/1/11 18:12
 * @Description:
 */
public interface TestService {
    String test();

    String uploadFile(MultipartFile file);
}
