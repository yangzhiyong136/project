package com.learning.project.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Youngz
 * @date 2019/8/18 - 23:07
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
