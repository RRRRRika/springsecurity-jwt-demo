# SpringSecurity-jwt-demo
一个基于 [JWT](https://jwt.io/) 的 demo.<br>

springboot 3.0 + springsecurity 6.0 + mysql + redis

## 思路
- 实现 UserDetails 接口
- 实现 UserDetailsService 接口
- 自定义 JWT Filter
- 实现 AccessDeniedHandler 和 AuthenticationEntryPoint 自定义失败处理
- 在 config 中进行配置

使用 redis 将存放已登录用户信息，减少查询数据库次数，即：
- 登录时放入 redis，设置过期时间
- 再次访问时，先从 redis 查询是否是已登录用户，非登录用户会拒绝响应
- 登出时直接将对应用户信息从 redis 删除

*****
### 关于认证流程
实际上就这么些步骤 ~~摸了好久, 我是菜鸡（虽然初次接触~~ ：
- Filter 将 username 和 password 封装在 Authentication 中传给 AuthenticationManagement
- Management 调用 AuthenticationProvider(s) 验证
- provider 从 UserDetailsService 获得 UserDetails, 并用 PasswordEncoder 验证
- 验证成功会返回 Authentication, 并将 principal（UserDetails）放在里面。失败会抛出 AuthenticationException
- 成功返回的 Authentication 会被放在上下文中（SecurityContext）

[官方文档](https://docs.spring.io/spring-security/reference/index.html) 中有更详细的描述，这里只是简单的概括