package com.mpf.website;

import com.mpf.website.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebsiteApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.getUserInfo();
    }

}
