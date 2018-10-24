package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.models.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase instance;

    public abstract MovieDao movieDao();
}
