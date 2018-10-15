package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.LoginUser;
import com.example.nzliveservice.bean.NameRecord;
import com.example.nzliveservice.bean.Student;
import com.example.nzliveservice.dao.UserLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
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


    @RequestMapping(value = "setNameRecord")
    @ResponseBody
    public JSONObject setNameRecord(HttpServletRequest request){
//        System.out.println(nameRecord.toString());
//
//        byte[] bytes=nameRecord.getImagebyte().getBytes();
//
//        byte2File(bytes,"d://","aaa.jpg");

        MultipartHttpServletRequest parms= (MultipartHttpServletRequest) request;
        List<MultipartFile> files= ((MultipartHttpServletRequest) request).getFiles("imagebyte");
        String userid=parms.getParameter("userid");
        System.out.println("userid:"+userid);
        MultipartFile file=null;
        BufferedOutputStream stream=null;
        for (int i=0;i<files.size();++i){
            file=files.get(i);
            if (!file.isEmpty()){
                try {
                    byte[] bytes=file.getBytes();
                    System.out.println(bytes.toString());
//                    byte2File(bytes ,"d://","ppp.jpg");
                    stream=new BufferedOutputStream(new FileOutputStream(new File("d://"+file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                    System.out.println("正在上传");
                } catch (IOException e) {
                    stream=null;
                    e.printStackTrace();
                    System.out.println("You failed to upload " + i + " => " + e.getMessage());
                }
            }else {
                System.out.println("You failed to upload " + i + " because the file was empty.");
            }
            System.out.println("上传成功");
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userid","1111111");
        return jsonObject;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
