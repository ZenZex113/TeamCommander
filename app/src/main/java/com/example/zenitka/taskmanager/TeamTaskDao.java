package com.example.zenitka.taskmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TeamTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TeamTask ttask);

    @Delete
    void delete(TeamTask ttask);

    @Query("DELETE FROM list_of_teamtasks")
    void deleteAll();

    @Query("SELECT * from list_of_teamtasks ORDER BY tdate_ms ASC")
    LiveData<List<TeamTask>> getAllTasksSortedByDate();

    @Query("SELECT * from list_of_teamtasks ORDER BY tstatus ASC")
    LiveData<List<TeamTask>> getAllTasksSortedByStatus();

    @Query("SELECT * from list_of_teamtasks ORDER BY tpriority ASC")
    LiveData<List<TeamTask>> getAllTasksSortedByPriority();

    @Query("SELECT * from list_of_teamtasks ORDER BY TUID ASC")
    LiveData<List<TeamTask>> getAllTasksSortedByUID();

    @Query("SELECT * from list_of_teamtasks WHERE parentUID = :parentUID ORDER BY TUID ASC")
    LiveData<List<TeamTask>> getProjectTeamTasks(int parentUID);
}
