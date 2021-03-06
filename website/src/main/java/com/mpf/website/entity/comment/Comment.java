package com.mpf.website.entity.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String articleId;
    private String content;
    private String createUser;
    private Date createTime;
    private Date updateTime;
}
