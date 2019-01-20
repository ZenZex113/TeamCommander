package com.example.zenitka.taskmanager.TeamTask_;

import android.os.Parcel;
import android.os.Parcelable;

public class TeamTask implements Parcelable {

    public int UID;
    public String name;
    String date;
    Long date_ms;
    int status = 0;
    int priority = 0;
    String desc = "";
    Long worker = null;

    TeamTask() {

    }
    TeamTask(TeamTask task) {
        this.name = task.name;
        this.date = task.date;
        this.date_ms = task.date_ms;
        this.status = task.status;
        this.priority = task.priority;
        this.desc = task.desc;
        this.UID = task.UID;
        this.worker = task.worker;
    }

    protected TeamTask(Parcel in) {
        UID = in.readInt();
        name = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            date_ms = null;
        } else {
            date_ms = in.readLong();
        }
        status = in.readInt();
        priority = in.readInt();
        desc = in.readString();
        worker = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(UID);
        dest.writeString(name);
        dest.writeString(date);
        if (date_ms == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(date_ms);
        }
        dest.writeInt(status);
        dest.writeInt(priority);
        dest.writeString(desc);
        dest.writeLong(worker);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TeamTask> CREATOR = new Parcelable.Creator<TeamTask>() {
        @Override
        public TeamTask createFromParcel(Parcel in) {
            return new TeamTask(in);
        }

        @Override
        public TeamTask[] newArray(int size) {
            return new TeamTask[size];
        }
    };
}
