package com.learning.project.mapper;

import com.learning.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Youngz
 * @date 2019/8/5 - 23:09
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (account_id,name,token,gmt_create,gmt_modified) VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
