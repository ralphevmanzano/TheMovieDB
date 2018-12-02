package com.ralphevmanzano.themoviedb.data.local.dao;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

@Dao
public abstract class MovieDao extends BaseDao<Movie> {

    @Query("SELECT * FROM TABLE_MOVIE")
    public abstract Flowable<List<Movie>> getMovies();

    @Query("SELECT * FROM TABLE_MOVIE WHERE id = :id")
    public abstract Movie getMovie(long id);

    @Transaction
    @Query("SELECT id, title, posterPath, backdropPath, category FROM TABLE_MOVIE WHERE category LIKE '%' || :category || '%'")
    public abstract Flowable<List<MinimizedMovie>> getMiniminzedMovies(String category);

    @Transaction
    @Query("SELECT id, title, posterPath, backdropPath, category FROM table_movie WHERE category LIKE '%' || :category || '%' ORDER BY releaseDate DESC")
    public abstract Flowable<List<MinimizedMovie>> getNowPlayingMovies(String category);

    @Override
    public void upsert(List<Movie> objList) {
        List<Long> insertResult = insert(objList);
        List<Movie> updateList = new ArrayList<>();

        for (int i = 0; i < insertResult.size(); i++) {
            if (insertResult.get(i) == -1) {
                Movie temp = getMovie(objList.get(i).getId());
                if (!temp.getCategory().contains(objList.get(i).getCategory())) {
                    temp.setCategory(temp.getCategory().concat(objList.get(i).getCategory()));
                    updateList.add(temp);
                }
            }
        }

        if (!updateList.isEmpty()) {
            update(updateList);
        }
    }

}
