package com.mpf.website.service.article.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mpf.website.dto.ArticleDTO;
import com.mpf.website.entity.article.Article;
import com.mpf.website.entity.user.User;
import com.mpf.website.mapper.ArticleMapper;
import com.mpf.website.service.article.ArticleService;
import com.mpf.website.util.DateUtil;
import com.mpf.website.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Integer saveArticle(ArticleDTO articleDTO){
        int res = -1;
        Article article = new Article(articleDTO);
        Integer articleId = article.getId();
        String filePath = "";
        if(articleId != null && articleId > 0){
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq("id", article.getId());
            Article old = articleMapper.selectOne(wrapper);
            // 判断是否已存在
            if(old != null && StringUtils.isNoneBlank(filePath = old.getFilePath())) {
                article.setUpdateTime(new Date());
                res = articleMapper.updateById(article);
            }
        } else {
            // 不存在则新建
            filePath = System.getProperty("user.dir") + File.separator + "upload"
                    + File.separator + articleDTO.getTitle() + ".txt";
            article.setFilePath(filePath);
            article.setCreateTime(new Date());
            res = articleMapper.insert(article);
        }
        // 将markdown数据保存到文件
        FileUtil.appendToFile(articleDTO.getContent(), filePath);
        return res;
    }

    @Override
    public ArticleDTO detailArticle(Integer articleId) {
        if(articleId != null && articleId > 0){
            Article old = articleMapper.selectById(articleId);
            // 判断是否已存在
            String filePath = "";
            if(old != null && StringUtils.isNoneBlank(filePath = old.getFilePath())) {
                File file = new File(filePath);
                if(file.exists()) {
                    String content = FileUtil.readFile(filePath);
                    ArticleDTO article = new ArticleDTO(old);
                    article.setContent(content);
                    return article;
                }
            }
        }
        return null;
    }

    @Override
    public Page<Article> listArticle(Page<Article> page, QueryWrapper<Article> wrapper) {
        return articleMapper.selectPage(page, wrapper);
    }
}
