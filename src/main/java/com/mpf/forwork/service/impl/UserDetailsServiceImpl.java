package com.mpf.forwork.service.impl;

import com.mpf.forwork.entity.JwtUser;
import com.mpf.forwork.entity.User;
import com.mpf.forwork.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by echisan on 2018/6/23
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(s);
        return new JwtUser(user);
    }
}
