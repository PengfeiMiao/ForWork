package com.mpf.website.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mpf.website.dto.article.ArticleDTO;
import lombok.Data;

import java.util.Date;

@Data
public class Article {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String author;
    private String title;
    private String intro;
    private String tags;
    private String filePath;
    private Integer star;
    private Integer visit;
    private Integer comment;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public Article() {
    }

    public Article(ArticleDTO articleDTO) {
        this.id = articleDTO.getId();
        this.author = articleDTO.getAuthor();
        this.title = articleDTO.getTitle();
        this.intro = articleDTO.getIntro();
        this.star = articleDTO.getStar();
        this.visit = articleDTO.getVisit();
        this.comment = articleDTO.getComment();
        this.status = articleDTO.getStatus();
        this.tags = articleDTO.getTags();
    }
}
