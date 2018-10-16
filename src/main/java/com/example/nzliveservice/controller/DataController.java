package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.LoginUser;
import com.example.nzliveservice.bean.NameRecord;
import com.example.nzliveservice.bean.Student;
import com.example.nzliveservice.dao.UserLoginDao;
import com.example.nzliveservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class DataController {

    @Autowired
    UserLoginDao userLoginDao;

//    @Value("${text1}") private String text1;

    @Value("${obtainDataStdentUrl}")
    private String obtainDataStdentUrl;

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


    /**
     *
     * @param request
     * @return 0:上传成功 1:上传失败 2:文件是空的 3:保存数据库失败
     */
    @RequestMapping(value = "setNameRecord")
    @ResponseBody
    public JSONObject setNameRecord(HttpServletRequest request){
//        System.out.println(nameRecord.toString());
//
//        byte[] bytes=nameRecord.getImagebyte().getBytes();
//
//        byte2File(bytes,"d://","aaa.jpg");

//        System.out.println(obtainDataStdentUrl);

        JSONObject jsonObject=new JSONObject();
        MultipartHttpServletRequest parms= (MultipartHttpServletRequest) request;
        List<MultipartFile> files= ((MultipartHttpServletRequest) request).getFiles("imagebyte");
        String userid=parms.getParameter("userid");
//        System.out.println("userid:"+userid);

        Util.mkdirPath(obtainDataStdentUrl);

        MultipartFile file=null;
        BufferedOutputStream stream=null;
        for (int i=0;i<files.size();++i){
            file=files.get(i);
            String url=obtainDataStdentUrl;
            String fileName=file.getOriginalFilename();
            String fileData=fileName.substring(0,8);
            Util.mkdirPath(obtainDataStdentUrl+fileData+"/");
//            System.out.println(fileData);
            String fileYear=fileName.substring(14,18);
            Util.mkdirPath(obtainDataStdentUrl+fileData+"/"+fileYear+"/");
//            System.out.println(fileYear);
            String fileSystem=fileName.substring(18,20);
            Util.mkdirPath(obtainDataStdentUrl+fileData+"/"+fileYear+"/"+fileSystem+"/");
//            System.out.println(fileSystem);
            String fileClass=fileName.substring(20,22);
            Util.mkdirPath(obtainDataStdentUrl+fileData+"/"+fileYear+"/"+fileSystem+"/"+fileClass+"/");
//            System.out.println(fileClass);
            url=obtainDataStdentUrl+fileData+"/"+fileYear+"/"+fileSystem+"/"+fileClass+"/";


            if (!file.isEmpty()){
                try {
                    byte[] bytes=file.getBytes();
//                    System.out.println(bytes.toString());
//                    byte2File(bytes ,"d://","ppp.jpg");
                    stream=new BufferedOutputStream(new FileOutputStream(new File(url+fileName)));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("正在上传");
                } catch (IOException e) {
                    stream=null;
                    e.printStackTrace();
                    System.out.println("您无法上传 " + i + " => " + e.getMessage());
                    jsonObject.put("status","1");
                    return jsonObject;
                }
            }else {
                System.out.println("您无法上传 " + i + " 因为文件是空的.");
                jsonObject.put("status","2");
                return jsonObject;
            }
            System.out.println("上传成功");

            try {
                userLoginDao.setNameRecord(userid,fileData,url+fileName);
                System.out.println("保存数据库成功");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("保存数据库失败");
                jsonObject.put("status","3");
                return jsonObject;
            }
        }
        jsonObject.put("status","0");
        return jsonObject;
    }

}