package com.mpf.website.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private Integer id;
    private String author;
    private String title;
    private String content;
    private Integer star;
    private Integer visit;
    private Integer comment;
    private Integer status;
}
