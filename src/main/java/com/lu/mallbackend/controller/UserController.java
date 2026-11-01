package com.lu.mallbackend.controller;

import com.lu.mallbackend.entity.User;
import com.lu.mallbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin // 允许前端跨域访问
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test() {
        return "后端跑通了！";
    }

    // 正确的写法：只有一层方法，不要嵌套
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        // 直接调用 mapper 里写好的方法
        return userMapper.findByUsername(user.getUsername());
    }
}