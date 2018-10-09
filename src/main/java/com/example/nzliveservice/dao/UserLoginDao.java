package com.example.nzliveservice.dao;

import com.example.nzliveservice.bean.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLoginDao {

    /**
     * 查询登入账号密码
     * @param username
     * @return
     */
    @Select("select * from user where username=#{username}")
    UserLogin getUserLogin(@Param("username") String username);
}
