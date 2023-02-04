package com.rika.demo.mapper;

import com.rika.demo.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select uuid, name, password, role, create_time as createTime from users " +
            "where uuid = #{uuid}")
    public User getUserByUUID(@Param("uuid") String uuid);

    @Select("select uuid, name, password, role, create_time as createTime from users " +
            "where name = #{name}")
    public User getUserByName(@Param("name") String name);

    @Insert("insert into users (uuid, name, password, role, create_time) " +
            "values (#{user.uuid}, #{user.name}, #{user.password}, #{user.role}, #{user.createTime})")
    public void addUser(@Param("user") User user);

    @Delete("delete from users where uuid = #{uuid}")
    public void deleteUser(@Param("uuid") String uuid);
}
