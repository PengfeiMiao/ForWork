package com.mpf.forwork;

import com.mpf.forwork.entity.User;
import com.mpf.forwork.mapper.UserMapper;
import com.mpf.forwork.service.kafka.KafkaProducer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ForworkApplicationTests {
//    @Autowired
//    private UserMapper userMapper;

    @Test
    public void testSelect() {
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
//        userList.forEach(System.out::println);
    }

}
