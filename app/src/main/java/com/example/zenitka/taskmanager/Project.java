package com.example.zenitka.teamcommander;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "list_of_projects")
public class Project implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int UID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "parentUID")
    public int parentUID;

    @ColumnInfo(name = "team_id")
    public Integer team_id;

    @ColumnInfo(name = "info")
    public String info;

    @ColumnInfo(name = "id")
    @NonNull
    public Integer id;

    Project() {

    }

    Project(Project project) {
        this.name = project.name;
        this.date = project.date;
        this.UID = project.UID;
        this.parentUID = project.parentUID;
        this.info = project.info;
        this.team_id = project.team_id;
        this.id = project.id;
    }

    protected Project(Parcel in) {
        UID = in.readInt();
        name = in.readString();
        date = in.readString();
        parentUID = in.readInt();
        info = in.readString();
        team_id = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(UID);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeInt(parentUID);
        dest.writeString(info);
        dest.writeInt(team_id);
        dest.writeInt(id);
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



