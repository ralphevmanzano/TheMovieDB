package com.ralphevmanzano.themoviedb.data.local.dao;

import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public abstract class MovieDetailsDao extends BaseDao<MovieDetails> {

    @Query("SELECT * FROM table_movie_details WHERE id = :id")
    public abstract Flowable<MovieDetails> getMovieDetails(Long id);
}
