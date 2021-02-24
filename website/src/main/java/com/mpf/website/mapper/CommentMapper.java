package com.mpf.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mpf.website.entity.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
