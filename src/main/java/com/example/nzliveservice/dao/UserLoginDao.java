package com.example.nzliveservice.dao;

import com.example.nzliveservice.bean.Student;
import com.example.nzliveservice.bean.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserLoginDao {

    /**
     * 学生查询登入账号密码
     * @param userid
     * @return
     */
    @Select("select * from student where userid=#{userid}")
    Student getUserStudentLogin(@Param("userid") String userid);

    /**
     * 老师查询登入账号密码
     * @param userid
     * @return
     */
    @Select("select * from teacher where userid=#{userid}")
    Teacher getUserTeacherLogin(@Param("userid") String userid);

    /**
     *查询所有学生
     * @return
     */
    @Select("select * from student")
    List<Student> getObtainDataStdent();

    /**
     * 查询点名上传记录
     * @param userid
     * @param data
     * @param url
     */
    @Insert("INSERT INTO namerecord (userid, data, url) VALUES (#{userid}, #{data}, #{url})")
    void setNameRecord(@Param("userid") String userid,@Param("data") String data,@Param("url") String url);
}
