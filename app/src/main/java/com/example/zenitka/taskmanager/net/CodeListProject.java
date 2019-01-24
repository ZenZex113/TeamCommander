package com.example.zenitka.taskmanager.net;

import com.example.zenitka.taskmanager.Project;
import com.example.zenitka.taskmanager.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodeListProject {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("response")
    @Expose
    private List<Project> response;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Project> getResponse() {
        return response;
    }

    public void setResponse(List<Project> response) {
        this.response = response;
    }
}
