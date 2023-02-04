package com.rika.demo.Service;

import com.rika.demo.entity.LoginUser;
import com.rika.demo.entity.User;
import com.rika.demo.entity.net.Req;
import com.rika.demo.entity.net.Resp;
import com.rika.demo.utils.JWTUtils;
import com.rika.demo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class AuthenticationService {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Resp login(Req request) {
        var token = new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword());
        // 验证失败会抛出异常 AuthenticationException
        authenticationManager.authenticate(token);

        // 验证成功, 将用户信息放入 redis 并返回 jwt
        var user = userService.getUserByName(request.getName());
        var jwt = JWTUtils.createToken(user.getUuid(), Map.of("name", user.getName(), "role", user.getRole()));
        redisCache.set(user.getUuid(), new LoginUser(user), 3600);
        return new Resp("登陆成功", jwt);
    }

    public Resp register(Req request) {
        User newUser = new User();
        newUser.setUuid(String.valueOf(UUID.randomUUID()));
        newUser.setName(request.getName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("USER");
        newUser.setCreateTime(new Date(System.currentTimeMillis()));

        userService.addNewUser(newUser);
        String jwt = JWTUtils.createToken(newUser.getUuid(), Map.of("name", newUser.getName(), "role", newUser.getRole()));
        redisCache.set(newUser.getUuid(), new LoginUser(newUser), 3600);
        // 注册成功也返回 jwt
        return new Resp("成功注册", jwt);
    }

    public Resp logout() {
        // 从上下文获取用户 uuid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uuid = ((LoginUser) authentication.getPrincipal()).getUsername();
        // 删除存在 redis 中的信息
        redisCache.del(uuid);
        return  new Resp("注销成功", null);
    }

}
