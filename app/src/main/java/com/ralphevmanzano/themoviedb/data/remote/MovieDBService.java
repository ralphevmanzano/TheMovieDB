package com.ralphevmanzano.themoviedb.data.remote;

import com.ralphevmanzano.themoviedb.data.remote.model.MovieResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/upcoming")
    Flowable<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key);

    @GET("movie/popular")
    Flowable<MovieResponse> getPopularMovies(@Query("api_key") String api_key);

}
