package com.mpf.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mpf.website.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
