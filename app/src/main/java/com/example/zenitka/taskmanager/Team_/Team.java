package com.example.zenitka.taskmanager.Team_;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.zenitka.taskmanager.Project_.Project;

import java.util.List;

public class Team implements Parcelable {
    /*public long UID;*/
    public String name;
    /*public List<User> users;
    public List<Project> projects;*/

    Team () {

    }
    Team(Team team) {
        this.name = team.name;
//        this.UID = team.UID;
        /*this.users = team.users;
        this.projects = team.projects;*/
    }

    protected Team(Parcel in) {
//        UID = in.readInt();
        name = in.readString();
        /*users = in.readArrayList(User);
        projects = in.readArrayList();*/
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(UID);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
