package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.models.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MovieDao {
	
	@Query("SELECT * FROM TABLE_MOVIE")
	Single<List<Movie>> getMovies();
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertMovie(Movie movie);

	@Query("SELECT COUNT(*) FROM TABLE_MOVIE")
	Single<Integer> getNumberOfRows();
	
}
