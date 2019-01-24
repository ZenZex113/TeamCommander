package com.example.zenitka.taskmanager.net;

import com.example.zenitka.taskmanager.Project;
import com.example.zenitka.taskmanager.Task;
import com.example.zenitka.taskmanager.TeamTask;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodeListTask {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("response")
    @Expose
    private List<TeamTask> response;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<TeamTask> getResponse() {
        return response;
    }

    public void setResponse(List<TeamTask> response) {
        this.response = response;
    }
}
