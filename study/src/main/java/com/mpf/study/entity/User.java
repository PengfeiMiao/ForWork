package com.mpf.study.entity;

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
@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_SINGLETON)
public class User implements Cloneable, Serializable {
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

    /**
     * 定义一个bean对象
     *
     * @return
     */
    @Bean(value = "user0", initMethod = "initUser", destroyMethod = "destroyUser")
    public User getUser() {
        System.out.println("创建user实例");
        return new User(1, "MPF");
    }

    public void initUser() {
        System.out.println("初始化用户bean之前执行");
    }

    public void destroyUser() {
        System.out.println("bean销毁之后执行");
    }

    @SneakyThrows
    @Override
    public User clone() {
        /*User o = null;
        try {
            o = (User)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //上面已经可以做到浅层克隆了，深层克隆就需要解决对象的对象属性
        return o;*/
        //将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        //从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (User) oi.readObject();
    }
}
