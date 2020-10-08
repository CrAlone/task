package com.cr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(value = "handler")
public class Info implements Serializable {
    private Integer info_id;
    private State state;
    private Level level;
    private TaskStaff staff;
    private TaskStaff taskStaff;
    private String info_name;
    private String info_desc;
    private String info_create_date;
    private String info_end_not_ate;
    private String info_end_date;
    public Info() {
    }

    public Info(Integer info_id, State state, Level level, TaskStaff staff, TaskStaff taskStaff, String info_name, String info_desc, String info_create_date, String info_end_not_ate, String info_end_date) {
        this.info_id = info_id;
        this.state = state;
        this.level = level;
        this.staff = staff;
        this.taskStaff = taskStaff;
        this.info_name = info_name;
        this.info_desc = info_desc;
        this.info_create_date = info_create_date;
        this.info_end_not_ate = info_end_not_ate;
        this.info_end_date = info_end_date;
    }

    public Integer getInfo_id() {
        return info_id;
    }

    public void setInfo_id(Integer info_id) {
        this.info_id = info_id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public TaskStaff getStaff() {
        return staff;
    }

    public void setStaff(TaskStaff staff) {
        this.staff = staff;
    }

    public TaskStaff getTaskStaff() {
        return taskStaff;
    }

    public void setTaskStaff(TaskStaff taskStaff) {
        this.taskStaff = taskStaff;
    }

    public String getInfo_name() {
        return info_name;
    }

    public void setInfo_name(String info_name) {
        this.info_name = info_name;
    }

    public String getInfo_desc() {
        return info_desc;
    }

    public void setInfo_desc(String info_desc) {
        this.info_desc = info_desc;
    }

    public String getInfo_create_date() {
        return info_create_date;
    }

    public void setInfo_create_date(String info_create_date) {
        this.info_create_date = info_create_date;
    }

    public String getInfo_end_not_ate() {
        return info_end_not_ate;
    }

    public void setInfo_end_not_ate(String info_end_not_ate) {
        this.info_end_not_ate = info_end_not_ate;
    }

    public String getInfo_end_date() {
        return info_end_date;
    }

    public void setInfo_end_date(String info_end_date) {
        this.info_end_date = info_end_date;
    }

    @Override
    public String toString() {
        return "Info{" +
                "info_id=" + info_id +
                ", state=" + state +
                ", level=" + level +
                ", staff=" + staff +
                ", taskStaff=" + taskStaff +
                ", info_name='" + info_name + '\'' +
                ", info_desc='" + info_desc + '\'' +
                ", info_create_date='" + info_create_date + '\'' +
                ", info_end_not_ate='" + info_end_not_ate + '\'' +
                ", info_end_date='" + info_end_date + '\'' +
                '}';
    }
}
