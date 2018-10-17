package com.example.nzliveservice.bean;


public class NameRecord {
    private String serianumber;
    private String userid;
    private String data;
    private String url;

    @Override
    public String toString() {
        return "NameRecord{" +
                "serianumber='" + serianumber + '\'' +
                ", userid='" + userid + '\'' +
                ", data='" + data + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getSerianumber() {
        return serianumber;
    }

    public void setSerianumber(String serianumber) {
        this.serianumber = serianumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
