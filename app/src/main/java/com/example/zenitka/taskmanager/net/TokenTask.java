package com.example.zenitka.taskmanager.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenTask {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("project_id")
    @Expose
    private Integer project_id;

    public TokenTask(String token, Integer project_id) {
        this.token = token;
        this.project_id = project_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }
}
