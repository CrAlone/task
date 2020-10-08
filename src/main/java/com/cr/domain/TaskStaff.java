package com.cr.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = "handler")
public class TaskStaff implements Serializable {
    private Integer staff_id;
    private TaskDepartment department;
    private String staff_name;
    private String staff_workCode;
    private String staff_password;
    private Integer staff_ads;
    private String staff_receiveMail;

    public TaskStaff() {
    }

    public TaskStaff(Integer staff_id, TaskDepartment department, String staff_name, String staff_workCode, String staff_password, Integer staff_ads, String staff_receiveMail) {
        this.staff_id = staff_id;
        this.department = department;
        this.staff_name = staff_name;
        this.staff_workCode = staff_workCode;
        this.staff_password = staff_password;
        this.staff_ads = staff_ads;
        this.staff_receiveMail = staff_receiveMail;
    }

    public Integer getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public TaskDepartment getDepartment() {
        return department;
    }

    public void setDepartment(TaskDepartment department) {
        this.department = department;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_workCode() {
        return staff_workCode;
    }

    public void setStaff_workCode(String staff_workCode) {
        this.staff_workCode = staff_workCode;
    }

    public String getStaff_password() {
        return staff_password;
    }

    public void setStaff_password(String staff_password) {
        this.staff_password = staff_password;
    }

    public Integer getStaff_ads() {
        return staff_ads;
    }

    public void setStaff_ads(Integer staff_ads) {
        this.staff_ads = staff_ads;
    }

    public String getStaff_receiveMail() {
        return staff_receiveMail;
    }

    public void setStaff_receiveMail(String staff_receiveMail) {
        this.staff_receiveMail = staff_receiveMail;
    }

    @Override
    public String toString() {
        return "TaskStaff{" +
                "staff_id=" + staff_id +
                ", department=" + department +
                ", staff_name='" + staff_name + '\'' +
                ", staff_workCode='" + staff_workCode + '\'' +
                ", staff_password='" + staff_password + '\'' +
                ", staff_ads=" + staff_ads +
                ", staff_receiveMail='" + staff_receiveMail + '\'' +
                '}';
    }
}
