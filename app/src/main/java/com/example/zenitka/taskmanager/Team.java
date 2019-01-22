package com.example.zenitka.taskmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "list_of_teams")
public class Team implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long UID;

    @ColumnInfo(name = "bd_id")
    public long bd_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "desc")
    public String desc;

    /*@ColumnInfo(name = "users")
    public List<User> users;

    @ColumnInfo(name = "projects")
    public List<Project> projects;*/

    Team() {

    }

    Team(Team team) {
        this.name = team.name;
        this.UID = team.UID;
        this.desc = team.desc;
        this.bd_id = team.bd_id;
       /* this.users = team.users;
        this.projects = team.projects;*/
    }

    protected Team(Parcel in) {
        UID = in.readLong();
        bd_id = in.readLong();
        name = in.readString();
        desc = in.readString();
        /*if (in.readByte() == 0x01) {
            users = new ArrayList<User>();
            in.readList(users, User.class.getClassLoader());
        } else {
            users = null;
        }
        if (in.readByte() == 0x01) {
            projects = new ArrayList<Project>();
            in.readList(projects, Project.class.getClassLoader());
        } else {
            projects = null;
        }*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(UID);
        dest.writeLong(bd_id);
        dest.writeString(name);
        dest.writeString(desc);
        /*if (users == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(users);
        }
        if (projects == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(projects);
        }*/
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
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