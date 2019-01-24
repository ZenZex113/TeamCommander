package com.example.zenitka.teamcommander;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TeamTaskViewModel extends AndroidViewModel {
    private TeamTaskRepository mRepository;

    private LiveData<List<TeamTask>> mAllTTasks;

    public TeamTaskViewModel (Application application) {
        super(application);
        mRepository = new TeamTaskRepository(application);
        mAllTTasks = mRepository.getAllTasksSortedByDate();
    }

    LiveData<List<TeamTask>> getAllTasksSortedByDate () {
        return mAllTTasks;
    }

    LiveData<List<TeamTask>> getAllTasksSortedByStatus () {
        return mRepository.getAllTasksSortedByStatus();
    }

    LiveData<List<TeamTask>> getAllTasksSortedByPriority () {
        return mRepository.getAllTasksSortedByPriority();
    }

    LiveData<List<TeamTask>> getAllTasksSortedByUID () {
        return mRepository.getAllTasksSortedByUID();
    }

    LiveData<List<TeamTask>> getProjectTeamTasks (int parentUID) {
        return mRepository.getProjectTeamTasks(parentUID);
    }

    public void insert(TeamTask ttask) {
        mRepository.insert(ttask);
    }

    public void delete(TeamTask ttask) {
        mRepository.delete(ttask);
    }

    public void deleteAll() { mRepository.deleteAll();}
}
