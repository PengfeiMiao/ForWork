package com.mpf.forwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mpf.forwork.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/31 15:31
 */
@Component
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username = #{s}")
    User findByUsername(String s);
}