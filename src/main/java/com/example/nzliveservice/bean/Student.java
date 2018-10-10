package com.example.nzliveservice.bean;

public class Student {
    private String userid;
    private String userpwd;
    private String dormroom;
    private String username;

    @Override
    public String toString() {
        return "Student{" +
                "userid='" + userid + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", dormroom='" + dormroom + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getDormroom() {
        return dormroom;
    }

    public void setDormroom(String dormroom) {
        this.dormroom = dormroom;
    }
}
