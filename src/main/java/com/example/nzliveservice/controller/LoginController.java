package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @RequestMapping(value = "login")
    @ResponseBody
    private String login(@RequestBody UserLogin userLogin){
        System.out.println(userLogin.toString());

        return "asdasd";
    }
}
