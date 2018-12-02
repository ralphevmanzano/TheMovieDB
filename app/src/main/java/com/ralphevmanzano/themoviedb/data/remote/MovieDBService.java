package com.ralphevmanzano.themoviedb.data.remote;

import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.models.VideosResponse;
import com.ralphevmanzano.themoviedb.data.models.MovieResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/{category}")
    Flowable<MovieResponse> getMovies(@Path("category") String category, @Query("api_key") String api_key);

    @GET("movie/{id}")
    Flowable<MovieDetails> getMovieDetails(@Path("id") Long id, @Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Flowable<VideosResponse> getMovieVideos(@Path("id") Long id, @Query("api_key") String api_key);
}
