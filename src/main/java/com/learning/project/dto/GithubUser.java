package com.learning.project.dto;

import lombok.Data;

/**
 * @author Youngz
 * @date 2019/8/3 - 22:51
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;//用户简介
    private String avatar_url;

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }*/

}
