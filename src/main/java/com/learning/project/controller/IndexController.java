package com.learning.project.controller;

import com.learning.project.dto.PaginationDTO;
import com.learning.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Youngz
 * @date 2019/7/30 - 17:16
 */
@Controller
public class IndexController {
    /* @Autowired
     private UserMapper userMapper;*/
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(Model model,
                        //分页功能
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size

    ) {
       /* Cookie[] cookies = request.getCookies();//获取cookie中的token，判断是否登录
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);//在数据库中寻找
                    if (user != null) {//获取用户信息不为空
                        request.getSession().setAttribute("user", user);//把封装好的对象，传到前端
                    }
                    break;
                }

            }*/
        PaginationDTO pagination = questionService.list(page, size);//还有封装了QuestionService里的信息
        model.addAttribute("pagination", pagination);//把封装好的对象，传到前端
        return "index";
    }

}
