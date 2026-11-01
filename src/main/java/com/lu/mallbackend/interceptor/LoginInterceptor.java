package com.lu.mallbackend.interceptor;

import com.lu.mallbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行浏览器的跨域预检请求（OPTIONS 请求不带 Token）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 从请求头中获取 token (前端通常放在 Authorization 字段里)
        String token = request.getHeader("Authorization");

        // 3. 验证 token
        if (token == null || token.trim().isEmpty()) {
            response.setStatus(401); // 401 代表未登录或未授权
            return false;
        }

        try {
            // 解析 token，如果过期或伪造，会抛出异常
            JwtUtils.parseToken(token);
            return true; // 验证通过，放行！
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}