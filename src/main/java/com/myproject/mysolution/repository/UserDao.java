package com.myproject.mysolution.repository;

import com.myproject.mysolution.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    @Select("SELECT count(*) FROM users")
    int count() throws Exception;
    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(String id) throws Exception;
    @Select("SELECT * FROM users WHERE id = #{user.id} AND name = #{user.name}")
    User selectByName(@Param("user") Map userInfo) throws Exception;
    @Select("SELECT * FROM users")
    List<User> selectAll() throws Exception;
    @Insert("INSERT INTO users (id, password, name, phone, email, created, updated) " +
            "VALUES (#{user.id}, #{user.password}, #{user.name}, #{user.phone}, #{user.email}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    int insert(@Param("user") User user) throws Exception;
    @Update("UPDATE users SET password = #{user.password}, name = #{user.name}, updated = CURRENT_TIMESTAMP " +
            "WHERE id = #{user.id}")
    int update(@Param("user") Map userInfo) throws Exception;
    @Delete("DELETE FROM users WHERE id = #{id}")
    int delete(String id) throws Exception;
    @Delete("DELETE FROM users")
    int deleteAll() throws Exception;
}
