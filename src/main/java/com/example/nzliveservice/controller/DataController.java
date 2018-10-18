package com.example.nzliveservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.*;
import com.example.nzliveservice.dao.UserDataDao;
import com.example.nzliveservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class DataController {

    @Autowired
    UserDataDao userDataDao;

//    @Value("${text1}") private String text1;

    @Value("${obtainDataStdentUrl}")
    private String obtainDataStdentUrl;

    @RequestMapping(value = "obtainDataStdent")
    @ResponseBody
    public JSONArray obtaindatastdent(@RequestBody LoginUser loginUser){

        //?需要判断用户名
        System.out.println(loginUser.toString());

        JSONArray jsonArray=new JSONArray();

        List<Student> students= userDataDao.getObtainDataStdent();
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
     * 获取及时点名文件接口
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
                userDataDao.setNameRecord(userid,fileData,url+fileName);
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


    @RequestMapping(value = "getImageStream")
    public ResponseEntity<FileSystemResource> getImageStream(@RequestBody ImageStream imageStream){
        System.out.println(imageStream.toString());
        String status=imageStream.getStatus();
        String yy=status.substring(0,2);
        String mm=status.substring(3,5);
        String ss=status.substring(6,8);
        String fileName=imageStream.getData()+yy+mm+ss+imageStream.getUserid()+".jpg";

        String userid=imageStream.getUserid();
        String fileUrl=obtainDataStdentUrl+imageStream.getData()+"/"+userid.substring(0,4)+"/"+userid.substring(4,6)+"/"+userid.substring(6,8)+"/";

        File file=new File(fileUrl+fileName);
        if (file==null){
            System.out.println("文件不存在！");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".jpg");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
//        return null;
    }

    /**
     * 个人点名记录
     * @return
     */
    @RequestMapping(value = "getNamerecordToOne")
    @ResponseBody
    public JSONArray getNamerecordToOne(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toString());

        List<NameRecord> mList= userDataDao.getNamerecordToOne(jsonObject.getString("userid")+"");
//        for (NameRecord nameRecord:mList){
//            System.out.println(nameRecord.toString());
//        }
        JSONArray jsonArray=new JSONArray();
        for (NameRecord nameRecord:mList){
            String url = nameRecord.getUrl();
            String[] strings=url.split("/");
            String data=strings[strings.length-1];
            String date=data.substring(0,8);
            String time=data.substring(8,14);
            String hh=time.substring(0,2);
            String MM=time.substring(2,4);
            String ss=time.substring(4,6);
            JSONObject object=new JSONObject();
            object.put("date",date);
            object.put("time",hh+":"+MM+":"+ss);
            jsonArray.add(object);
        }
        return jsonArray;
    }

    /**
     * 上传教师点名记录
     * @param initiateNameRecord
     * @return 0:上传成功 1,数据库错误
     */
    @RequestMapping(value = "setInitiateNameRecord")
    @ResponseBody
    public JSONObject setInitiateNameRecord(@RequestBody InitiateNameRecord initiateNameRecord){
        System.out.println(initiateNameRecord.toString());

        JSONObject jsonObject=new JSONObject();

        try {
            userDataDao.setInitiateNameRecord(initiateNameRecord.getUserid(),initiateNameRecord.getDate(),initiateNameRecord.getTime());
            jsonObject.put("status","0");
            return jsonObject;
        }catch (Exception e){
            jsonObject.put("status","1");
            return jsonObject;
        }
    }

    @RequestMapping(value = "getInitiateNameRecord")
    @ResponseBody
    public JSONArray getInitiateNameRecord(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toString());
        String userid=jsonObject.getString("userid");
        List<InitiateNameRecord> mList=userDataDao.getInitiateNameRecord(userid);
        JSONArray mJsonArray=new JSONArray();
        for (InitiateNameRecord record:mList){
            JSONObject object=new JSONObject();
            object.put("date",record.getDate());
            object.put("time",record.getTime());
            mJsonArray.add(object);
        }
        return mJsonArray;
    }

    /**
     * 新增报修记录
     * @param repair
     * @return 0:提交成功 1:提交失败
     */
    @RequestMapping(value = "setRepairData")
    @ResponseBody
    public JSONObject setRepairData(@RequestBody Repair repair){
        System.out.println(repair.toString());
        JSONObject object=new JSONObject();
        try {
            userDataDao.setRepairData(repair.getUserid(),repair.getUsername(),repair.getDormroom(),repair.getNum(),repair.getData(),repair.getDate(),repair.getSchedule());
            object.put("status",0);
        }catch (Exception e){
            e.printStackTrace();
            object.put("status",1);
        }
        return object;
    }
}
