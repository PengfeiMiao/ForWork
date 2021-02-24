package com.mpf.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mpf.website.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/31 15:31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where name = #{s}")
    User findByUsername(String s);
}