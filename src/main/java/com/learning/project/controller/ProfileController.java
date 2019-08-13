package com.learning.project.controller;

import com.learning.project.dto.PaginationDTO;
import com.learning.project.model.User;
import com.learning.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Youngz
 * @date 2019/8/12 - 22:01
 */
@Controller
public class ProfileController {
 /*   @Autowired
    private UserMapper userMapper;*/

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")//动态响应
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
      //  User user = null;
     /*   Cookie[] cookies = request.getCookies();//获取cookie中的token，判断是否登录
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);//在数据库中寻找
                    if (user != null) {//获取用户信息不为空
                        request.getSession().setAttribute("user", user);//把封装好的对象，传到前端
                    }
                    break;
                }

            }
        }*/

        if (user == null) {
            return "redirect:/";
        }
        //避免空指针
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        //page，inset，user
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination", paginationDTO);//把封装好的对象，传到前端

        return "profile";
    }

}
