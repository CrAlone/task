package com.cr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(value = "handler")
public class TaskDepartment implements Serializable {
    private Integer department_id;
    private String department_name;
    private String department_code;
    private List<TaskStaff> staffs;
    public TaskDepartment() {
    }
    public TaskDepartment(Integer department_id, String department_name, String department_code) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.department_code = department_code;
    }

    public List<TaskStaff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<TaskStaff> staffs) {
        this.staffs = staffs;
    }

    public Integer getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }
    public String getDepartment_name() {
        return department_name;
    }
    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
    public String getDepartment_code() {
        return department_code;
    }
    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    @Override
    public String toString() {
        return "TaskDepartment{" +
                "department_id=" + department_id +
                ", department_name='" + department_name + '\'' +
                ", department_code='" + department_code + '\'' +
                ", staffs=" + staffs +
                '}';
    }
}
