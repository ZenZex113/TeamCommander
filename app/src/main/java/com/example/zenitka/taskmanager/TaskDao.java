package com.example.zenitka.teamcommander;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM list_of_tasks")
    void deleteAll();

    @Query("SELECT * from list_of_tasks ORDER BY date_ms ASC")
    LiveData<List<Task>> getAllTasksSortedByDate();

    @Query("SELECT * from list_of_tasks ORDER BY status ASC")
    LiveData<List<Task>> getAllTasksSortedByStatus();

    @Query("SELECT * from list_of_tasks ORDER BY priority ASC")
    LiveData<List<Task>> getAllTasksSortedByPriority();

    @Query("SELECT * from list_of_tasks ORDER BY UID ASC")
    LiveData<List<Task>> getAllTasksSortedByUID();
}
