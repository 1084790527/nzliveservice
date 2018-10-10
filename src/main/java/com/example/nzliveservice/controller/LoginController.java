package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.LoginUser;
import com.example.nzliveservice.bean.Student;
import com.example.nzliveservice.bean.Teacher;
import com.example.nzliveservice.dao.UserLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class LoginController {

    @Autowired
    UserLoginDao userLoginDao;

    /**
     * 登入接口
     * @param loginUser
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    private JSONObject login(@RequestBody LoginUser loginUser){
        System.out.println(loginUser.toString());
        JSONObject jsonObject=new JSONObject();
//        System.out.println("AAA:"+student.getUserid().length());
        if (loginUser.getUserid().length()==6){
            Teacher user=userLoginDao.getUserTeacherLogin(loginUser.getUserid());
        }
        Student user=userLoginDao.getUserStudentLogin(loginUser.getUserid());
        if (user==null){
            //账号不存在
            jsonObject.put("status","0");
            return  jsonObject;
        }else if (!user.getUserpwd().equals(loginUser.getUserpwd())){
            //密码错误
            jsonObject.put("status","1");
            return  jsonObject;
        }else {
            //账号密码正确
            jsonObject.put("status","2");
            jsonObject.put("userid",user.getUserid());
            jsonObject.put("dormroom",user.getDormroom());
            jsonObject.put("username",user.getUsername());
            jsonObject.put("userpwd",user.getUserpwd());
            System.out.println(loginUser.toString());
            System.out.println(""+user.toString());
            return jsonObject;
        }

    }
}
