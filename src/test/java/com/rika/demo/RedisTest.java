package com.rika.demo;

import com.rika.demo.entity.LoginUser;
import com.rika.demo.entity.User;
import com.rika.demo.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisCache redisCache;

    private final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Test
    public void testRedis() {
        logger.info("#-------------------------------------------------");
        redisCache.set("test", 99999);
        logger.info("has key 'test'? : " + redisCache.hasKey("test"));
        logger.info("get key 'test': " + redisCache.get("test"));

        logger.info("#-------------------------------------------------");
        User user = new User();
        user.setUuid(String.valueOf(UUID.randomUUID()));
        user.setPassword("password");
        user.setRole("USER");
        user.setName("user");
        user.setCreateTime(new Date(System.currentTimeMillis()));
        logger.info(user.toString());
        redisCache.set("user", user);
        var u = redisCache.get("user");
        logger.info("get 'user' from redis: ");
        logger.info(u.toString());

        logger.info("#-------------------------------------------------");
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);
        logger.info(loginUser.toString());
        redisCache.set("loginUser", loginUser, 5);
        var l = redisCache.get("loginUser");
        logger.info("get 'loginUser' from redis: ");
        logger.info(l.toString());
        logger.info("user in the 'loginUser': ");
        logger.info(((LoginUser) l).getUser().toString());

        logger.info("#-------------------------------------------------");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisCache.del("test");
        logger.info("redis has 'test'? : " + redisCache.hasKey("test"));
        logger.info("redis has 'user'? : " + redisCache.hasKey("user"));
        logger.info("redis has 'loginUser'? : " + redisCache.hasKey("loginUser"));
        redisCache.del("user");
        logger.info("RedisTest finish.");
    }
}
