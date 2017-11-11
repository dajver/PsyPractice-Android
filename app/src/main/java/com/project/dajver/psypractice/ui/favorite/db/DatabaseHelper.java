package com.project.dajver.psypractice.ui.favorite.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;

/**
 * Created by gleb on 11/11/17.
 */

@Database(entities = { FavoriteNewsModel.class }, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract FavoriteNewsDao getFavoriteDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
