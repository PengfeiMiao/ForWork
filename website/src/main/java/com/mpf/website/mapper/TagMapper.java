package com.mpf.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mpf.website.entity.tag.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 18:05
 * @description:
 * @since: 1.0.20
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    @Delete("<script>" +
            "delete from tag where content in " +
            "<foreach item='item' collection='strArr' open='(' separator=',' close=')'>" +
                "#{item}" +
            "</foreach>" +
            "</script>")
    int deleteByContent(@Param("strArr") String[] strArr);
}
