package com.newproject.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Developer-X on 2016/11/14.
 */
public class User {
    @SerializedName("name")
    @Expose
    private String username;
    private String password;
    @SerializedName("_id")
    @Expose
    private long userId;
    @SerializedName("mobile")
    @Expose
    private String phone;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("BUDGET_vip")
    @Expose
    private boolean isBudgetVip;
    @SerializedName("HOMECOST_end_time")
    @Expose
    private Date endTime;

    private String checkcode;

    public boolean isBudgetVip() {
        return isBudgetVip;
    }

    public void setBudgetVip(boolean budgetVip) {
        isBudgetVip = budgetVip;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", isBudgetVip=" + isBudgetVip +
                ", endTime=" + endTime +
                ", checkcode='" + checkcode + '\'' +
                '}';
    }
}
