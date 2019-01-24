package com.example.zenitka.teamcommander;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;

    private LiveData<List<Task>> mAllTasks;

    public TaskViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasksSortedByDate();
    }

    LiveData<List<Task>> getAllTasksSortedByDate () {
        return mAllTasks;
    }

    LiveData<List<Task>> getAllTasksSortedByStatus () {
        return mRepository.getAllTasksSortedByStatus();
    }

    LiveData<List<Task>> getAllTasksSortedByPriority () {
        return mRepository.getAllTasksSortedByPriority();
    }

    LiveData<List<Task>> getAllTasksSortedByUID () {
        return mRepository.getAllTasksSortedByUID();
    }

    public void insert(Task task) {
        mRepository.insert(task);
    }

    public void delete(Task task) {
        mRepository.delete(task);
    }
}
