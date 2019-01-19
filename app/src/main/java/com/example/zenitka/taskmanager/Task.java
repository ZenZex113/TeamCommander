package com.example.zenitka.taskmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "list_of_tasks")
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int UID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "date_ms")
    Long date_ms;

    @ColumnInfo(name = "status")
    int status = 0;

    @ColumnInfo(name = "priority")
    int priority = 0;

    @ColumnInfo(name = "desc")
    String desc = "";

    Task() {

    }
    Task(Task task) {
        this.name = task.name;
        this.date = task.date;
        this.date_ms = task.date_ms;
        this.status = task.status;
        this.priority = task.priority;
        this.desc = task.desc;
        this.UID = task.UID;
    }


    protected Task(Parcel in) {
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}



