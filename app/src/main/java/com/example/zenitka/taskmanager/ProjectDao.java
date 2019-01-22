package com.example.zenitka.taskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Delete
    void delete(Project project);

    @Query("DELETE FROM list_of_projects")
    void deleteAll();

    @Query("SELECT * from list_of_projects ORDER BY UID ASC")
    LiveData<List<Project>> getAllProjectsSortedByDate();

}
