package com.ralphevmanzano.themoviedb.network;

import com.ralphevmanzano.themoviedb.data.models.MovieResponse;


import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDBService {
	
	@GET("discover/movie")
	Flowable<MovieResponse> getMovies(@Query("api_key") String api_key);
}
