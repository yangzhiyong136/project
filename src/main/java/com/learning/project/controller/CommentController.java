package com.learning.project.controller;

import com.learning.project.dto.CommentDTO;
import com.learning.project.dto.ResultDTO;
import com.learning.project.exception.CustomizeErrorCode;
import com.learning.project.model.Comment;
import com.learning.project.model.User;
import com.learning.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Youngz
 * @date 2019/8/16 - 22:17
 */
//返回Json
@Controller
//请求拿到Json，后台拿到Json反序列化一个对象，
// 给前端也是Object，由SpringBoot把对象转换为Json传回前端
public class CommentController {
    /*@Autowired
    private CommentMapper commentMapper;*/
    @Autowired
    private CommentService commentService;

    @ResponseBody//自动数列化json，返回给前端
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    //请求Json传输
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        //  commentMapper.insert(comment);//插入数据库
        //  如果一个属性值要加判断，抽出Service层，业务逻辑层，加以判断再调用
        commentService.insert(comment);//插入数据库
        return ResultDTO.okOf();
    }
}
