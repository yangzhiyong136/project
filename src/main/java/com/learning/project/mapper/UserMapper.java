package com.learning.project.mapper;

import com.learning.project.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Youngz
 * @date 2019/8/5 - 23:09
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (account_id,name,token,gmt_create,gmt_modified,avatar_url)" +
            " VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);
}


