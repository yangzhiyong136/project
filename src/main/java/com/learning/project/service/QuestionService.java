package com.learning.project.service;

import com.learning.project.dto.QuestionDTO;
import com.learning.project.mapper.QuestionMapper;
import com.learning.project.mapper.UserMapper;
import com.learning.project.model.Question;
import com.learning.project.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Youngz
 * @date 2019/8/8 - 22:39
 */
/*中间层*/
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    
    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question对象中属性全部copy给questionDTO对象
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            //每次创建新的DTO要把它加进去
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
