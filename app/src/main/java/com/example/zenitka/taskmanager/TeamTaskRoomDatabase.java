package com.example.zenitka.taskmanager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {TeamTask.class}, version = 1)
public abstract class TeamTaskRoomDatabase extends RoomDatabase {
    public abstract TeamTaskDao ttaskDao();

    private static volatile TeamTaskRoomDatabase INSTANCE;

    static TeamTaskRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (TeamTaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TeamTaskRoomDatabase.class, "teamtask_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){

        }
    };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TeamTaskDao mDao;

        PopulateDbAsync(TeamTaskRoomDatabase db) {
            mDao = db.ttaskDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
