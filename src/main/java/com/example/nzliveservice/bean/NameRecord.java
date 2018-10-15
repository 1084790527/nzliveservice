package com.example.nzliveservice.bean;

import java.util.Arrays;

public class NameRecord {
    private String userid;
    private String imagebyte;

    @Override
    public String toString() {
        return "NameRecord{" +
                "userid='" + userid + '\'' +
                ", imagebyte='" + imagebyte + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImagebyte() {
        return imagebyte;
    }

    public void setImagebyte(String imagebyte) {
        this.imagebyte = imagebyte;
    }
}
