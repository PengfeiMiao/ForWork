package com.mpf.forwork.controller;

import com.google.gson.Gson;
import com.mpf.forwork.annotation.SysLogs;
import com.mpf.forwork.entity.User;
import com.mpf.forwork.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/6/18 11:38
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    /**
     * 调用数据库日志工厂方法将日志记录到数据库
     */
    public final static Logger logger = LoggerFactory.getLogger("databaseLogger");

    @Autowired
    RedisUtil redisUtil;

    @Resource
    User user;

    @SysLogs
    @GetMapping("/do")
    public void doTest() {
        //String[] keys = {"0", "1"};
        System.out.println("hasKey=>"+redisUtil.hasKey("0")+"\n"+redisUtil.hasKey("1"));

        redisUtil.del("0", "1", "2");

        Map<String,Object> map = new HashMap<>(10);
        map.put("1", "test1");
        map.put("2", "test2");
        redisUtil.set("0",map);

        redisUtil.hset("1","k1","v1");
        redisUtil.hset("1","k2","v2");

        System.out.println(redisUtil.getExpire("1"));

        Map<String,Object> map1 = new HashMap<>(10);
        RedisController.objectToMap(redisUtil.get("0"));

        System.out.println("get=>"+map1.get("1"));

        System.out.println("hget=>"+redisUtil.hget("1","k1"));

        redisUtil.hmset("2", map);

        Map<Object, Object> map2 = redisUtil.hmget("2");

        System.out.println("hmget=>"+map2.get("1"));

        //Map遍历:方法一
        for(Map.Entry<String, Object> entry : map.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue().toString();
            System.out.println(mapKey+":"+mapValue);
        }
        //Map遍历:方法二
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, Object> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue().toString();
            System.out.println(key+":"+value);
        }

        logger.info("redis测试完毕");

    }

    @GetMapping("/set")
    public void setTest() {
        user.setUsername("MPF");
    }

    @GetMapping("/get")
    public void getTest() {
        user = user.getUser();
        System.out.println(user.getUsername());
    }

    public static Map<String, String> objectToMap(Object obj) {
        if(obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>(10);

        if(!StringUtils.isEmpty(obj.toString())){
            //先转成json字符，再转回json对象，JSON实际上是实现Map接口的子类，所以可以直接赋值给Map对象
            Gson gson = new Gson();
            map = gson.fromJson(gson.toJson(obj), Map.class);
        }
        return map;
    }
}
