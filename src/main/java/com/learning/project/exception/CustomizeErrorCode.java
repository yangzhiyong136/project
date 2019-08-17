package com.learning.project.exception;

/**
 * @author Youngz
 * @date 2019/8/15 - 17:57
 */
//通用异常管理，在页面手动输入处理的请求，返回一个用户能看懂的错误页
//封装错误信息
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选择任何问题进行回复"),
    NOT_LOGIN(2003, "未登录不能评论，请先登录"),
    SYS_ERROR(2004,"服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在或错误"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空");
    private String message;
    private Integer code;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    /* CustomizeErrorCode(String message) {
         this.message = message;
     }
 */
    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
