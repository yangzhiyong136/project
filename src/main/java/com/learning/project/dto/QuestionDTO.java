package com.learning.project.dto;

import com.learning.project.model.User;
import lombok.Data;

/**
 * @author Youngz
 * @date 2019/8/8 - 22:37
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;

}
