package com.example.zenitka.taskmanager.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenProject {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("team_id")
    @Expose
    private Integer task_id;

    public TokenProject(String token, Integer task_id) {
        this.token = token;
        this.task_id = task_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }
}
