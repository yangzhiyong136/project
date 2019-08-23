package com.learning.project.controller;

import com.learning.project.dto.PaginationDTO;
import com.learning.project.model.User;
import com.learning.project.service.NotificationService;
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
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")//动态响应
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");


        if (user == null) {
            return "redirect:/";
        }
        //避免空指针
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            //page，inset，user
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);//把封装好的对象，传到前端
        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);//把封装好的对象，传到前端
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }

}
