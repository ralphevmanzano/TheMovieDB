package com.ralphevmanzano.themoviedb.data.local;

import com.ralphevmanzano.themoviedb.data.DateConverter;
import com.ralphevmanzano.themoviedb.data.local.dao.MovieDao;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class}, version = 4)
@TypeConverters(DateConverter.class)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
