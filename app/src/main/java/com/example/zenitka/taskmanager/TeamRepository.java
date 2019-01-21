package com.example.zenitka.taskmanager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TeamRepository {

    private TeamDao mTeamDao;
    private LiveData<List<Team>> mAllTeams;

    TeamRepository (Application application) {
        TeamRoomDatabase db = TeamRoomDatabase.getDatabase(application);
        mTeamDao = db.teamDao();
        mAllTeams = mTeamDao.getAllTeams();
    }
    LiveData<List<Team>> getAllTeams () {
        return mAllTeams;
    }

    public void insert(Team team) {
        new TeamRepository.insertAsyncTeam(mTeamDao).execute(team);
    }

    public void delete(Team team) {
        new TeamRepository.deleteAsyncTeam(mTeamDao).execute(team);
    }

    private static class insertAsyncTeam extends AsyncTask<Team, Void, Void> {

        private TeamDao mAsyncTaskDao;

        insertAsyncTeam(TeamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTeam extends AsyncTask<Team, Void, Void> {

        private TeamDao mAsyncTaskDao;

        deleteAsyncTeam(TeamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
