package com.ralphevmanzano.themoviedb.data.local.dao;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM TABLE_MOVIE WHERE category = 1")
    Flowable<List<Movie>> getUpcomingMovies();

    @Query("SELECT * FROM TABLE_MOVIE WHERE category = 2")
    Flowable<List<Movie>> getPopularMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("SELECT COUNT(*) FROM TABLE_MOVIE")
    Single<Integer> getNumberOfRows();

}
