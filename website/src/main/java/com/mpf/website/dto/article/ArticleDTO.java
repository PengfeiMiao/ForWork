package com.mpf.website.dto.article;

import com.mpf.website.entity.article.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

@Data
@ApiModel
public class ArticleDTO {
    @ApiModelProperty("博客id")
    private Integer id;
    @ApiModelProperty("作者")
    private String author;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("摘要")
    private String intro;
    @ApiModelProperty("markdown数据")
    private String content;
    @ApiModelProperty("点赞数")
    private Integer star;
    @ApiModelProperty("访问量")
    private Integer visit;
    @ApiModelProperty("评论数")
    private Integer comment;
    @ApiModelProperty("文章状态")
    private Integer status;
    @ApiModelProperty("文章标签")
    private String tags;

    public ArticleDTO(){}

    public ArticleDTO(Article ariticle) {
        try {
            BeanUtils.copyProperties(this, ariticle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
