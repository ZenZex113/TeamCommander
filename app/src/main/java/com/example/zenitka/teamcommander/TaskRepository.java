package com.example.zenitka.teamcommander;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    TaskRepository (Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getAllTasksSortedByDate();
    }
    LiveData<List<Task>> getAllTasksSortedByDate () {
        return mAllTasks;
    }

    LiveData<List<Task>> getAllTasksSortedByStatus () {
        return mTaskDao.getAllTasksSortedByStatus();
    }

    LiveData<List<Task>> getAllTasksSortedByPriority () {
        return mTaskDao.getAllTasksSortedByPriority();
    }

    LiveData<List<Task>> getAllTasksSortedByUID () {
        return mTaskDao.getAllTasksSortedByUID();
    }

    public void insert(Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }

    public void delete(Task task) {
        new deleteAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        deleteAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
