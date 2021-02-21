package com.mpf.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/18 16:14
 * @description:
 * @since: 1.0.20
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index"; //当浏览器输入/index时，会返回 /static/index.html的页面
    }
}
