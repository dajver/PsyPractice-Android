package com.project.dajver.psypractice;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.project.dajver.psypractice.ui.favorite.db.DatabaseHelper;

/**
 * Created by gleb on 11/11/17.
 */

public class App extends Application {

    private static App instance;
    private DatabaseHelper db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "favorite-database")
                .allowMainThreadQueries()
                .build();
    }

    public DatabaseHelper getDatabaseInstance() {
        return db;
    }
}
