package com.learning.project.dto;

import lombok.Data;

/**
 * @author Youngz
 * @date 2019/8/22 - 18:15
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
