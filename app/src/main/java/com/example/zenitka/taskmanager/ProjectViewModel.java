package com.example.zenitka.taskmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository mRepository;

    private LiveData<List<Project>> mAllProjects;

    public ProjectViewModel (Application application) {
        super(application);
        mRepository = new ProjectRepository(application);
        mAllProjects = mRepository.getAllProjectsSortedByDate();
    }

    LiveData<List<Project>> getAllProjectsSortedByDate () {
        return mAllProjects;
    }

    public void insert(Project project) {
        mRepository.insert(project);
    }

    public void delete(Project project) {
        mRepository.delete(project);
    }

    public LiveData<List<Project>> getTeamProjects(int parentUID) {
        return mRepository.getTeamProjects(parentUID);
    }

    public void deleteAll() { mRepository.deleteAll();}
}
