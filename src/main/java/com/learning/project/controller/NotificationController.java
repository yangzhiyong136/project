package com.learning.project.controller;

import com.learning.project.dto.NotificationDTO;
import com.learning.project.enums.NotificationTypeEnum;
import com.learning.project.model.User;
import com.learning.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Youngz
 * @date 2019/8/19 - 22:49
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")//动态响应
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id,
                          Model model) {
        User user = (User) request.getSession().getAttribute("user");


        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);//校验
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }


    }
}
