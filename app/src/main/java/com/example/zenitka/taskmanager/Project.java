package com.example.zenitka.taskmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable{

    public int UID;
    public String name;
    public String date;
    public Long date_ms;
    public String desc = "";

    Project() {

    }
    Project(Project project) {
        this.name = project.name;
        this.date = project.date;
        this.date_ms = project.date_ms;
        this.desc = project.desc;
        this.UID = project.UID;
    }

    protected Project(Parcel in) {
        UID = in.readInt();
        name = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            date_ms = null;
        } else {
            date_ms = in.readLong();
        }
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(UID);
        dest.writeString(name);
        dest.writeString(date);
        if (date_ms == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(date_ms);
        }
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
