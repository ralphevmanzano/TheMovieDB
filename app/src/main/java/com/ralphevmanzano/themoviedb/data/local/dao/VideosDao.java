package com.ralphevmanzano.themoviedb.data.local.dao;

import com.ralphevmanzano.themoviedb.data.local.entity.Video;
import com.ralphevmanzano.themoviedb.data.models.VideosResponse;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public abstract class VideosDao extends BaseDao<Video> {

    @Query("SELECT * FROM table_movie_details_video WHERE movieId = :id")
    public abstract Flowable<Video> getMovieVideo(Long id);
}
