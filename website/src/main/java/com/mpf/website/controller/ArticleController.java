package com.mpf.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.mpf.website.dto.ArticleDTO;
import com.mpf.website.entity.article.Article;
import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.service.article.ArticleService;
import com.mpf.website.staticobj.CommonStatic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/article")
@Api(value = "博客接口", tags = "博客接口")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @ApiOperation(value = "保存博客", notes = "保存博客")
    @PostMapping("/save")
    @ResponseBody
    public RestResponse saveArticle(@ApiParam("博客数据") @RequestParam("article")ArticleDTO article) {
        RestResponse response = new RestResponse();
        try {
            Article article1 = new Article(article);
            Integer res = articleService.saveArticle(article1);
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(res);
        } catch (Exception e) {
            log.error("saveArticle fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
