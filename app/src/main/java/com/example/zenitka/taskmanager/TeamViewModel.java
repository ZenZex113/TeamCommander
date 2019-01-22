package com.example.zenitka.taskmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {

    private TeamRepository mRepository;

    private LiveData<List<Team>> mAllTasks;

    public TeamViewModel (Application application) {
        super(application);
        mRepository = new TeamRepository(application);
        mAllTasks = mRepository.getAllTeams();
    }

    LiveData<List<Team>> getAllTeams() {
        return mRepository.getAllTeams();
    }

    public void insert(Team team) {
        mRepository.insert(team);
    }

    public void delete(Team team) {
        mRepository.delete(team);
    }

    public void deleteAll() { mRepository.deleteAll();}
}
