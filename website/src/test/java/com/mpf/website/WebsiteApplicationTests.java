package com.mpf.website;

import com.mpf.website.mapper.TagMapper;
import com.mpf.website.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebsiteApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    TagMapper tagMapper;

    @Test
    void contextLoads() {
//        userService.getUsers();
        tagMapper.deleteByContent(new String[]{"1","2"});
    }

}
