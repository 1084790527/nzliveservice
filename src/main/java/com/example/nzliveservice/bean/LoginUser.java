package com.example.nzliveservice.bean;

public class LoginUser {
    private String userid;
    private String userpwd;

    @Override
    public String toString() {
        return "LoginUser{" +
                "userid='" + userid + '\'' +
                ", userpwd='" + userpwd + '\'' +
                '}';
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
}
