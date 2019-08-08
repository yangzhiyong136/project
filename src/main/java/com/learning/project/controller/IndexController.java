package com.learning.project.controller;

import com.learning.project.dto.QuestionDTO;
import com.learning.project.mapper.UserMapper;
import com.learning.project.model.User;
import com.learning.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Youngz
 * @date 2019/7/30 - 17:16
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionMapper;


    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();//获取cookie中的token，判断是否登录
       if(cookies != null && cookies.length!=0)
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);//在数据库中寻找
                if (user != null) {//获取用户信息不为空
                request.getSession().setAttribute("user",user);
                }
                break;
            }

        }

        List<QuestionDTO> questionList = questionMapper.list();
        model.addAttribute("questions",questionList);
        return "index";
    }

}
