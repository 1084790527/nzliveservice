package com.example.nzliveservice.bean;

public class ImageStream {
    private String userid;
    private String data;
    private String status;

    @Override
    public String toString() {
        return "ImageStream{" +
                "userid='" + userid + '\'' +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                '}';
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
