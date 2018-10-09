package com.example.nzliveservice.bean;

public class UserLogin {
    private String num;
    private String pwd;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "num='" + num + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
