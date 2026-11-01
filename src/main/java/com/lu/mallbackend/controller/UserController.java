package com.lu.mallbackend.controller;

import com.lu.mallbackend.entity.User;
import com.lu.mallbackend.mapper.UserMapper;
import com.lu.mallbackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
// @CrossOrigin 删掉，我们接下来会在 WebConfig 中配置全局跨域
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test() {
        return "后端跑通了，且你带上了合法的 Token！";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 查数据库
        User dbUser = userMapper.findByUsername(user.getUsername());

        // 2. 校验账号密码
        if (dbUser == null || !dbUser.getPassword().equals(user.getPassword())) {
            result.put("code", 400);
            result.put("msg", "用户名或密码错误");
            return result;
        }

        // 3. 生成 Token
        String token = JwtUtils.generateToken(dbUser.getId(), dbUser.getUsername());

        result.put("code", 200);
        result.put("msg", "登录成功");
        result.put("token", token);
        result.put("userInfo", dbUser); // 注意：如果你之前给 password 加了 @JsonIgnore，这里就不会返回密码
        return result;
    }
}