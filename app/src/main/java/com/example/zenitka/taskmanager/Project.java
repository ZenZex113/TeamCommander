package com.example.zenitka.taskmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "list_of_projects")
public class Project implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int UID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "date_ms")
    Long date_ms;

    Project() {

    }
    Project(Project project) {
        this.name = project.name;
        this.date = project.date;
        this.date_ms = project.date_ms;
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
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



