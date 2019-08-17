package com.learning.project.controller;

import com.learning.project.dto.CommentCreateDTO;
import com.learning.project.dto.CommentDTO;
import com.learning.project.dto.ResultDTO;
import com.learning.project.enums.CommentTypeEnum;
import com.learning.project.exception.CustomizeErrorCode;
import com.learning.project.model.Comment;
import com.learning.project.model.User;
import com.learning.project.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        //  commentMapper.insert(comment);//插入数据库
        //  如果一个属性值要加判断，抽出Service层，业务逻辑层，加以判断再调用
        commentService.insert(comment);//插入数据库
        return ResultDTO.okOf();
    }


    @ResponseBody//自动数列化json，返回给前端
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name ="id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        return ResultDTO.okOf(commentDTOS);
    }
}
