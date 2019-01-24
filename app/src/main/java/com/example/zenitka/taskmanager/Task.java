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

    @ColumnInfo(name = "description")
    String description = "";

    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "project_id")
    int project_id;

    @ColumnInfo(name = "executor_id")
    int executor_id = 0;

    @ColumnInfo(name = "watcher_id")
    int watcher_id = 0;

    @ColumnInfo(name = "deadline")
    int deadline = 0;

    Task() {

    }
    Task(Task task) {
        this.name = task.name;
        this.date = task.date;
        this.date_ms = task.date_ms;
        this.status = task.status;
        this.priority = task.priority;
        this.description = task.description;
        this.UID = task.UID;
        this.id = task.id;
        this.project_id = task.project_id;
        this.executor_id = task.executor_id;
        this.watcher_id = task.watcher_id;
        this.deadline = task.deadline;
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
        description = in.readString();
        id = in.readInt();
        project_id = in.readInt();
        executor_id = in.readInt();
        watcher_id = in.readInt();
        deadline = in.readInt();
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
        dest.writeString(description);
        dest.writeInt(id);
        dest.writeInt(project_id);
        dest.writeInt(executor_id);
        dest.writeInt(watcher_id);
        dest.writeInt(deadline);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
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



