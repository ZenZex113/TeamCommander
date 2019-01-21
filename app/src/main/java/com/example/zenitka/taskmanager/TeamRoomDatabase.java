package com.example.zenitka.taskmanager;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Team.class}, version = 1)
public abstract class TeamRoomDatabase extends RoomDatabase {

    public abstract TeamDao teamDao();

    private static volatile TeamRoomDatabase INSTANCE;

    static TeamRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (TeamRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TeamRoomDatabase.class, "team_database")
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

        private final TeamDao mDao;

        PopulateDbAsync(TeamRoomDatabase db) {
            mDao = db.teamDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
