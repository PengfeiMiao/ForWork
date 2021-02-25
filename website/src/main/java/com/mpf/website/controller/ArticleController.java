package com.mpf.website.controller;

import com.mpf.website.dto.ArticleDTO;
import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.service.article.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/article")
@Api(value = "博客接口", tags = "博客接口")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @ApiOperation(value = "保存博客", notes = "保存博客")
    @PostMapping("/save")
    @ResponseBody
    public RestResponse saveArticle(@ApiParam("博客数据") @RequestBody ArticleDTO article) {
        RestResponse response = new RestResponse();
        try {
            Integer res = articleService.saveArticle(article);
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(res);
        } catch (Exception e) {
            log.error("saveArticle fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "博客详情", notes = "博客详情")
    @PostMapping("/detail")
    @ResponseBody
    public RestResponse detailArticle(@ApiParam("博客id") @RequestParam("articleId") Integer articleId) {
        RestResponse response = new RestResponse();
        try {
            ArticleDTO data = articleService.detailArticle(articleId);
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(data);
        } catch (Exception e) {
            log.error("detailArticle fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
