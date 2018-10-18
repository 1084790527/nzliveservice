package com.example.nzliveservice.bean;

public class Repair {
    private String id;
    private String userid;
    private String username;
    private String dormroom;
    private String num;
    private String data;
    private String date;
    private int schedule;        //0:初次提交    1:辅导员审批通过    2:教务处审批通过    3:已报修

    @Override
    public String toString() {
        return "Repair{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", dormroom='" + dormroom + '\'' +
                ", num='" + num + '\'' +
                ", data='" + data + '\'' +
                ", date='" + date + '\'' +
                ", schedule=" + schedule +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDormroom() {
        return dormroom;
    }

    public void setDormroom(String dormroom) {
        this.dormroom = dormroom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }
}
