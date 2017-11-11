package com.project.dajver.psypractice.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.project.dajver.psypractice.db.model.FavoriteNewsModel;

import java.util.List;

/**
 * Created by gleb on 11/11/17.
 */

@Dao
public interface FavoriteNewsDao {
    @Insert
    void insert(FavoriteNewsModel favoriteNewsModel);

    @Delete
    void delete(FavoriteNewsModel favoriteNewsModel);

    @Query("SELECT * FROM FavoriteNewsModel")
    List<FavoriteNewsModel> getAllFavorites();
}
