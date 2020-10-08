package com.cr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "handler")
public class State {
    private Integer state_id;
    private String state_name;
    public State() {
    }
    public State(Integer state_id, String state_name) {
        this.state_id = state_id;
        this.state_name = state_name;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    @Override
    public String toString() {
        return "State{" +
                "state_id=" + state_id +
                ", state_name='" + state_name + '\'' +
                '}';
    }
}
