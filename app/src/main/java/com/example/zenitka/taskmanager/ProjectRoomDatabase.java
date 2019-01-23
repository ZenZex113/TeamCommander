package com.example.zenitka.taskmanager;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Project.class}, version = 1)
public abstract class ProjectRoomDatabase extends RoomDatabase {

    public abstract ProjectDao projectDao();

    private static volatile ProjectRoomDatabase INSTANCE;

    static ProjectRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ProjectRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProjectRoomDatabase.class, "project_database")
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

        private final ProjectDao mDao;

        PopulateDbAsync(ProjectRoomDatabase db) {
            mDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
