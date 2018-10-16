package com.example.nzliveservice.util;

import java.io.File;

public class Util {
    public static boolean mkdirPath(String url){
        File filePath=new File(url);
        try {
            if (!filePath.exists()){
                filePath.mkdir();
                return true;
            }
            return true;
        }catch (Exception e){
            System.out.println("新建目录操作出错");
            e.printStackTrace();
            return false;
        }
    }
}
