package com.learning.project.mapper;

import com.learning.project.model.User;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("update user set name = #{name},token = #{token}," +
            "gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl}" +
            "where id = #{id} ")
    void update(User user);
}


