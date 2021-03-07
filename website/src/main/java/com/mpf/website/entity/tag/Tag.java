package com.mpf.website.entity.tag;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 16:05
 * @description:
 * @since: 1.0.20
 */
@Data
public class Tag {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer count;
    private String createUser;
    private Date createTime;
    private Date updateTime;
    private Integer used;
}
