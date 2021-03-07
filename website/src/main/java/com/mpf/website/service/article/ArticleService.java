package com.mpf.website.service.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mpf.website.dto.article.ArticleDTO;
import com.mpf.website.entity.article.Article;


public interface ArticleService {
    Integer saveArticle(ArticleDTO article);

    ArticleDTO detailArticle(Integer id);

    Page<Article> listArticle(Page<Article> page, QueryWrapper<Article> wrapper);
}
