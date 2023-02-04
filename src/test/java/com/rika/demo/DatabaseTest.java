package com.rika.demo;

import com.rika.demo.Service.UserService;
import com.rika.demo.entity.User;
import com.rika.demo.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class DatabaseTest {

    private final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testUserService() {
        User user = new User();
        user.setUuid(String.valueOf(UUID.randomUUID()));
        user.setName("rika");
        user.setPassword(passwordEncoder.encode("rika"));
        user.setRole("USER");
        user.setCreateTime(new Date(System.currentTimeMillis()));
        logger.info(user.toString());
        try {
            userService.addNewUser(user);
            var q = userService.getUserByName("rika");
            logger.info("#-------------------------------------------");
            logger.info(q.toString());
            logger.info("#-------------------------------------------");
            logger.info(userService.getUserByUUID(user.getUuid()).toString());
            logger.info("#-------------------------------------------");
            userService.deleteUser(user.getUuid());
            logger.info("delete user " + user.toString());
            logger.info("#-------------------------------------------");
            logger.info(JWTUtils.createToken(String.valueOf(UUID.randomUUID()), Map.of("name", "rika", "role", "USER")));
            logger.info("test finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
