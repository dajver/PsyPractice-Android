package com.project.dajver.psypractice;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.project.dajver.psypractice.ui.favorite.db.DatabaseHelper;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

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
        Fabric.with(this, new Crashlytics());
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "favorite-database")
                .allowMainThreadQueries()
                .build();
    }

    public DatabaseHelper getDatabaseInstance() {
        return db;
    }
}
