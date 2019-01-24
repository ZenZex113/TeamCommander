package com.example.zenitka.taskmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "list_of_teamtasks")
public class TeamTask implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int TUID;

    @ColumnInfo(name = "tname")
    public String tname;

    @ColumnInfo(name = "tdate")
    String tdate;

    @ColumnInfo(name = "tdate_ms")
    Long tdate_ms;

    @ColumnInfo(name = "tstatus")
    int tstatus = 0;

    @ColumnInfo(name = "tpriority")
    int tpriority = 0;

    @ColumnInfo(name = "tdesc")
    String tdesc = "";

    @ColumnInfo(name = "tworker")
    String worker = null;

    @ColumnInfo(name = "parentUID")
    public int parentUID;

    @ColumnInfo(name = "project_id")
    public int project_id;

    @ColumnInfo(name = "executor_id")
    public int executor_id;

    @ColumnInfo(name = "watcher_id")
    public int watcher_id;

    @ColumnInfo(name = "id")
    public int id;

    public String token;        //Shhhhh!


    TeamTask() {
    }
    TeamTask(TeamTask task) {
        this.tname = task.tname;
        this.tdate = task.tdate;
        this.tdate_ms = task.tdate_ms;
        this.tstatus = task.tstatus;
        this.tpriority = task.tpriority;
        this.tdesc = task.tdesc;
        this.TUID = task.TUID;
        this.worker = task.worker;
        this.parentUID = task.parentUID;
        this.project_id = task.project_id;
        this.executor_id = task.executor_id;
        this.watcher_id = task.watcher_id;
        this.id = task.id;
    }

    protected TeamTask(Parcel in) {
        TUID = in.readInt();
        tname = in.readString();
        tdate = in.readString();
        if (in.readByte() == 0) {
            tdate_ms = null;
        } else {
            tdate_ms = in.readLong();
        }
        tstatus = in.readInt();
        tpriority = in.readInt();
        tdesc = in.readString();
        worker = in.readString();
        parentUID = in.readInt();
        project_id = in.readInt();
        executor_id = in.readInt();
        watcher_id = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(TUID);
        dest.writeString(tname);
        dest.writeString(tdate);
        if (tdate_ms == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(tdate_ms);
        }
        dest.writeInt(tstatus);
        dest.writeInt(tpriority);
        dest.writeString(tdesc);
        dest.writeString(worker);
        dest.writeInt(parentUID);
        dest.writeInt(project_id);
        dest.writeInt(executor_id);
        dest.writeInt(watcher_id);
        dest.writeInt(id);
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
