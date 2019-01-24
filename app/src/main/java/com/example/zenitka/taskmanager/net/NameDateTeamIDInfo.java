package com.example.zenitka.taskmanager.net;

public class NameDateTeamIDInfo {
    public final String token;

    public final String name, date, info;
    public final int team_id;

    public NameDateTeamIDInfo(String token, String name, String date, int team_id, String info) {
        this.token = token;
        this.name = name;
        this.info = info;
        this.team_id = team_id;
        this.date = date;
    }
}
