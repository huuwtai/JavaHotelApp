package com.example.bookingapp.Database;

import java.io.Serializable;

public class User implements Serializable {
    public String email;
    public String phone;
    public String sex;
    public String password;
    public String fullname;
    //empty constructor
    public  User(){}
    //constructor
    public User(String email, String phone, String sex, String password,String fullname) {
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.password = password;
        this.fullname = fullname;
    }
    //getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
