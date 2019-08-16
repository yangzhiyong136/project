package com.learning.project.mapper;

import com.learning.project.model.Question;

/**
 * @author Youngz
 * @date 2019/8/15 - 23:20
 */
public interface QuestionExtMapper {
    int incView(Question record);
    int inCommentCount(Question record);
}
