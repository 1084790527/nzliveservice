package com.example.nzliveservice.bean;

public class InitiateNameRecord {
    private String userid;
    private String date;
    private String time;

    @Override
    public String toString() {
        return "InitiateNameRecord{" +
                "userid='" + userid + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
