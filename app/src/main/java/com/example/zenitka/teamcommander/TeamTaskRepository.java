package com.example.zenitka.teamcommander;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TeamTaskRepository {
    private TeamTaskDao mTTaskDao;
    private LiveData<List<TeamTask>> mAllTTasks;

    TeamTaskRepository (Application application) {
        TeamTaskRoomDatabase db = TeamTaskRoomDatabase.getDatabase(application);
        mTTaskDao = db.ttaskDao();
        mAllTTasks = mTTaskDao.getAllTasksSortedByDate();
    }
    LiveData<List<TeamTask>> getAllTasksSortedByDate () {
        return mAllTTasks;
    }

    LiveData<List<TeamTask>> getAllTasksSortedByStatus () {
        return mTTaskDao.getAllTasksSortedByStatus();
    }

    LiveData<List<TeamTask>> getAllTasksSortedByPriority () {
        return mTTaskDao.getAllTasksSortedByPriority();
    }

    LiveData<List<TeamTask>> getAllTasksSortedByUID () {
        return mTTaskDao.getAllTasksSortedByUID();
    }

    public LiveData<List<TeamTask>> getProjectTeamTasks(int parentUID) {
        return mTTaskDao.getProjectTeamTasks(parentUID);
    }

    public void insert(TeamTask ttask) {
        new TeamTaskRepository.insertAsyncTask(mTTaskDao).execute(ttask);
    }

    public void delete(TeamTask ttask) {
        new TeamTaskRepository.deleteAsyncTask(mTTaskDao).execute(ttask);
    }

    private static class insertAsyncTask extends AsyncTask<TeamTask, Void, Void> {

        private TeamTaskDao mAsyncTaskDao;

        insertAsyncTask(TeamTaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TeamTask... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TeamTask, Void, Void> {

        private TeamTaskDao mAsyncTaskDao;

        deleteAsyncTask(TeamTaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TeamTask... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
