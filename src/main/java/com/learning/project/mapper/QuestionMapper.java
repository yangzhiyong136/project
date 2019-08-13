package com.learning.project.mapper;

import com.learning.project.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Youngz
 * @date 2019/8/7 - 22:43
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    //分页大小限制
    @Select("select * from question limit  #{offset},#{size} ")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    //评论总数
    @Select("select count(1) from question")
    Integer count();

    //方法不能重名
    @Select("select * from question where creator=#{userId} limit  #{offset},#{size} ")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId} ")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);
}