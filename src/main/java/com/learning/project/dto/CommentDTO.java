package com.learning.project.dto;

import com.learning.project.model.User;
import lombok.Data;

/**
 * @author Youngz
 * @date 2019/8/17 - 15:16
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Long commentCount;
    private String content;
    private User user;
}
