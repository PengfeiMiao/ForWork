package com.mpf.website.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mpf.website.entity.user.User;
import com.mpf.website.mapper.UserMapper;
import com.mpf.website.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUsers(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        //总页数+总记录数
        Page<User> page = new Page<>(1, 2);
        Page<User> users = userMapper.selectPage(page, wrapper);
        log.info("user:{}", users);
        return users.getRecords();
    }

}
