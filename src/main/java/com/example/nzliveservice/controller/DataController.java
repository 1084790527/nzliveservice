package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.LoginUser;
import com.example.nzliveservice.bean.Student;
import com.example.nzliveservice.dao.UserLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DataController {

    @Autowired
    UserLoginDao userLoginDao;

    @RequestMapping(value = "obtainDataStdent")
    @ResponseBody
    public JSONArray obtaindatastdent(@RequestBody LoginUser loginUser){

        //?需要判断用户名
        System.out.println(loginUser.toString());

        JSONArray jsonArray=new JSONArray();

        List<Student> students=userLoginDao.getObtainDataStdent();
        for (Student student:students){
//            System.out.println(student.toString());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userid",student.getUserid());
            jsonObject.put("dormroom",student.getDormroom());
            jsonObject.put("username",student.getUsername());
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }
}
