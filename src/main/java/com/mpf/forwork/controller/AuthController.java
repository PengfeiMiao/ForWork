package com.mpf.forwork.controller;

import com.mpf.forwork.entity.User;
import com.mpf.forwork.mapper.UserMapper;
import com.mpf.forwork.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by echisan on 2018/6/23
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRole("ROLE_USER");
        System.out.println(user);
        userMapper.insert(user);

        return user.toString();
    }

    @PostMapping("/registerAdmin")
    public String registerAdmin(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRole("ROLE_ADMIN");
        System.out.println(user);
        userMapper.insert(user);

        return user.toString();
    }
}
