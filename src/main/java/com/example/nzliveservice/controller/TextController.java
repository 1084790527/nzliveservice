package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;

@Controller
public class TextController {

    @RequestMapping(value = "text")
    @ResponseBody
    public String text(){
        return "text";
    }

    @RequestMapping(value = "t")
    public String t(){
        return "t";
    }

    @RequestMapping(value = "textjson")
    @ResponseBody
    public String textjson(@RequestBody JSONObject json){
//        System.out.println("asdasd:"+a+b);
        System.out.println(json.toString());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("1","q");
        jsonObject.put("2","w");
    return jsonObject.toString();
    }

    @RequestMapping(value = "textwebsocket")
    public String textwebsocket(){
        return "textwebsocket";
    }

    @RequestMapping(value = "text1")
    public String text1(){
        return "text1";
    }

    @RequestMapping(value = "/")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "home")
    public String home(String userid, Model model){
        System.out.println("userid:"+userid);
        if (userid==null){
            return "/";
        }
        model.addAttribute("userid",userid);
        return "home";
    }

}
