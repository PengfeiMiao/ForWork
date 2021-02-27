package com.mpf.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mpf.website.dto.ArticleDTO;
import com.mpf.website.dto.base.ConditionDTO;
import com.mpf.website.entity.article.Article;
import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.service.article.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
        if(log.isInfoEnabled()) {
            log.info("saveArticle: {}", article);
        }
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
    @GetMapping("/detail")
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
    @ApiOperation(value = "博客列表", notes = "博客列表")
    @PostMapping("/list")
    @ResponseBody
    public RestResponse detailArticle(
            @ApiParam("筛选条件") @RequestBody ConditionDTO<Article> condition
            ) {
        RestResponse response = new RestResponse();
        try {
            QueryWrapper<Article> wrapper = condition.generateQueryWrapper();
            Page<Article> pages = articleService.listArticle(condition.generatePageHelper(), wrapper);
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            Map<String, Object> data = new HashMap<>(2);
            data.put("list", pages.getRecords());
            data.put("total", pages.getTotal());
            response.setData(data);
        } catch (Exception e) {
            log.error("detailArticle fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
