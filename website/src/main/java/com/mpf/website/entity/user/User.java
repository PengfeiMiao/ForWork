package com.mpf.website.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/31 14:51
 */
@Data
public class User{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.username = name;
    }
}
