package com.rika.demo.filter;

import com.rika.demo.entity.LoginUser;
import com.rika.demo.utils.JWTUtils;
import com.rika.demo.utils.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 自定义 Filter, 验证 jwt
 *
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头取得 token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 没有 token 或 token 不正确
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = token.substring(7);
        String uuid = JWTUtils.getUUID(jwt);

        // 从 redis 中查看是否是已登陆用户
        LoginUser loginUser = (LoginUser) redisCache.get(uuid);
        if (loginUser == null) {
            logger.info("redis 中用户不存在: " + uuid);
            filterChain.doFilter(request, response);
            return;
        }

        // 将用户信息封装成 Authentication 放入上下文
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
