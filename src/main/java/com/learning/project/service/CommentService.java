package com.learning.project.service;

import com.learning.project.enums.CommentTypeEnum;
import com.learning.project.exception.CustomizeErrorCode;
import com.learning.project.exception.CustomizeException;
import com.learning.project.mapper.CommentMapper;
import com.learning.project.mapper.QuestionExtMapper;
import com.learning.project.mapper.QuestionMapper;
import com.learning.project.model.Comment;
import com.learning.project.model.Question;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Youngz
 * @date 2019/8/16 - 23:03
 */
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.inCommentCount(question);
        }
    }
}
