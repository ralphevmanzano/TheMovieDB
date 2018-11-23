package com.ralphevmanzano.themoviedb.data.local;

import com.ralphevmanzano.themoviedb.data.local.dao.MovieDao;
import com.ralphevmanzano.themoviedb.data.local.dao.MovieDetailsDao;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.local.typeconverters.DateConverter;
import com.ralphevmanzano.themoviedb.data.local.typeconverters.ListIntConverter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class, MovieDetails.class}, version = 9)
@TypeConverters({DateConverter.class, ListIntConverter.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract MovieDetailsDao movieDetailsDao();
}
