package com.mpf.website.entity.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 16:06
 * @description:
 * @since: 1.0.20
 */
@Data
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer count;
    private Integer sort;
    private Integer visible;
    private String createUser;
    private Date createTime;
    private Date updateTime;
    private Integer used;
}
