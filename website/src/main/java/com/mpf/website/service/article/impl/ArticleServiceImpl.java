package com.mpf.website.service.article.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mpf.website.entity.article.Article;
import com.mpf.website.mapper.ArticleMapper;
import com.mpf.website.service.article.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Integer saveArticle(Article article){

        Integer articleId = article.getId();
        if(articleId != null && articleId > 0){
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq("id", article.getId());
            Integer count = articleMapper.selectCount(wrapper);
            if(count > 0) {
                return articleMapper.updateById(article);
            }
        }
        return articleMapper.insert(article);
    }
}
