package com.learning.project.controller;

import com.learning.project.mapper.QuestionMapper;
import com.learning.project.model.Question;
import com.learning.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Youngz
 * @date 2019/8/7 - 22:05
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
   /* @Autowired
    private UserMapper userMapper;*/

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("publish")
    //不传入会报错，required = false
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model
            //接受参数
    ) {
        //使用model，可以把值返回到前端，前端获取到并显示出来，并且要放到最上面
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
       /* User user = null;
        Cookie[] cookies = request.getCookies();//获取cookie中的token，判断是否登录
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);//在数据库中寻找
                    if (user != null) {//获取用户信息不为空
                        request.getSession().setAttribute("user", user);//把封装好的对象，传到前端
                    }
                    break;
                }
            }*/
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录");//把封装好的对象，传到前端
            return "publish";
        }

        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtCreate(user.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
