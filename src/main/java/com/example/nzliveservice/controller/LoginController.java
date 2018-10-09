package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.UserLogin;
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
     * @param userLogin
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    private JSONObject login(@RequestBody UserLogin userLogin){

        JSONObject jsonObject=new JSONObject();

        UserLogin user=userLoginDao.getUserLogin(userLogin.getUsername());
        if (user==null){
            //账号不存在
            jsonObject.put("status","0");
            return  jsonObject;
        }else if (!user.getUserpwd().equals(userLogin.getUserpwd())){
            //密码错误
            jsonObject.put("status","1");
            return  jsonObject;
        }else {
            //账号密码正确
            jsonObject.put("status","2");
            System.out.println(userLogin.toString());
            System.out.println(""+user.toString());
            return jsonObject;
        }

    }
}
