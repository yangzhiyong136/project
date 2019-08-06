package com.learning.project.controller;

import com.learning.project.mapper.UserMapper;
import com.learning.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Youngz
 * @date 2019/7/30 - 17:16
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();//获取cookie中的token，判断是否登录
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);//在数据库中寻找
                if (user != null) {
                request.getSession().setAttribute("user",user);
                }
            }
            break;
        }
        return "index";
    }

}
