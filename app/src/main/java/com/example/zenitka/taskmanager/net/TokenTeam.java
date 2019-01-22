package com.example.zenitka.taskmanager.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenTeam {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("team_id")
    @Expose
    private Integer team_id;

    public TokenTeam(String token, Integer team_id) {
        this.token = token;
        this.team_id = team_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }
}
