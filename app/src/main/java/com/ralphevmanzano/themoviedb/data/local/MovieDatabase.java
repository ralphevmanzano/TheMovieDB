package com.ralphevmanzano.themoviedb.data.local;

import com.ralphevmanzano.themoviedb.data.local.dao.MovieDao;
import com.ralphevmanzano.themoviedb.data.local.dao.MovieDetailsDao;
import com.ralphevmanzano.themoviedb.data.local.dao.ReviewsDao;
import com.ralphevmanzano.themoviedb.data.local.dao.SuggestionsDao;
import com.ralphevmanzano.themoviedb.data.local.dao.VideosDao;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.local.entity.Video;
import com.ralphevmanzano.themoviedb.data.local.typeconverters.GenreConverter;
import com.ralphevmanzano.themoviedb.data.models.VideosResponse;
import com.ralphevmanzano.themoviedb.data.local.typeconverters.DateConverter;
import com.ralphevmanzano.themoviedb.data.local.typeconverters.ListIntConverter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Movie.class, MovieDetails.class, Video.class}, version = 14)
@TypeConverters({DateConverter.class, ListIntConverter.class, GenreConverter.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract MovieDetailsDao movieDetailsDao();
    public abstract VideosDao videosDao();
    public abstract ReviewsDao reviewsDao();
    public abstract SuggestionsDao suggestionsDao();
}
