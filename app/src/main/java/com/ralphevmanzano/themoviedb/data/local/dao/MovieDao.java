package com.ralphevmanzano.themoviedb.data.local.dao;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM TABLE_MOVIE WHERE category = :category")
    Flowable<List<Movie>> getMovies(int category);

    @Query("SELECT id, posterPath, backdropPath FROM TABLE_MOVIE WHERE category = :category")
    Flowable<List<MinimizedMovie>> getMoviesPosterPath(int category);

    @Query("SELECT id, posterPath, backdropPath FROM table_movie WHERE category = :category ORDER BY releaseDate DESC")
    Flowable<List<MinimizedMovie>> getNowPlayingMovies(int category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("SELECT COUNT(*) FROM TABLE_MOVIE")
    Single<Integer> getNumberOfRows();

}
