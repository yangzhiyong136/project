package com.learning.project.dto;

import lombok.Data;

/**
 * @author Youngz
 * @date 2019/8/16 - 22:25
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
