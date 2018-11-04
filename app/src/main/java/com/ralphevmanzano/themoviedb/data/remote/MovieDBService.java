package com.ralphevmanzano.themoviedb.data.remote;

import com.ralphevmanzano.themoviedb.data.remote.model.MovieResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/{category}")
    Flowable<MovieResponse> getMovies(@Path("category") String category, @Query("api_key") String api_key);
}
