package com.example.zenitka.taskmanager.Team_;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Team team);

    @Delete
    void delete(Team team);

    @Query("DELETE FROM list_of_teams")
    void deleteAll();

    @Query("SELECT * from list_of_teams ORDER BY UID ASC")
    LiveData<List<Team>> getAllTeams();
}
