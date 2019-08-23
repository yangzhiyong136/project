package com.learning.project.dto;

import lombok.Data;

/**
 * @author Youngz
 * @date 2019/8/19 - 21:37
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String outerTitle;
    private Long notifier;
    private String notifierName;
    private Long outerid;
    private String typeName;
    private Integer type;

}
