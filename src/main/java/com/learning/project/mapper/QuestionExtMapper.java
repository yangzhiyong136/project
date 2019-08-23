package com.learning.project.mapper;

import com.learning.project.dto.QuestionQueryDTO;
import com.learning.project.model.Question;

import java.util.List;

/**
 * @author Youngz
 * @date 2019/8/15 - 23:20
 */
public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
