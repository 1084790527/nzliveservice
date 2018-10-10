package com.example.nzliveservice.dao;

import com.example.nzliveservice.bean.Student;
import com.example.nzliveservice.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLoginDao {

    /**
     * 查询登入账号密码
     * @param userid
     * @return
     */
    @Select("select * from student where userid=#{userid}")
    Student getUserStudentLogin(@Param("userid") String userid);

    @Select("select * from Teacher where userid=#{userid}")
    Teacher getUserTeacherLogin(@Param("userid") String userid);
}
