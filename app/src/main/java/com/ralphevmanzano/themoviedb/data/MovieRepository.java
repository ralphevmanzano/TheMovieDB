package com.ralphevmanzano.themoviedb.data;

import android.annotation.SuppressLint;

import com.ralphevmanzano.themoviedb.data.models.Movie;
import com.ralphevmanzano.themoviedb.data.models.MovieResponse;
import com.ralphevmanzano.themoviedb.network.MovieDBService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieRepository {
    private static final String TAG = "MovieRepository";
    private static final long FRESH_TIMEOUT_IN_MINUTES = 1 * 1000 * 60;
    private long lastCall = 0;
    private MovieDao movieDao;
    private MovieDBService movieDBService;

    @Inject
    MovieRepository(MovieDBService movieDBService, MovieDao movieDao) {
        this.movieDBService = movieDBService;
        this.movieDao = movieDao;

    }

    @SuppressLint("CheckResult")
    public Flowable<List<Movie>> getMovieList() {
        if (refreshMovieList()) {
            Timber.d("getMovies from api");
            movieDBService.getMovies("da7a27b5f804b0d194c5ae906088f7c4")
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResult, this::handleError);
        }

        return movieDao.getMovies();
    }

    private void handleResult(MovieResponse movieResponse) {
        for (Movie movie : movieResponse.getMovies()) {
            Completable.fromAction(() -> movieDao.insertMovie(movie))
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    private void handleError(Throwable t) {
        Timber.e(t, "onError: error fetching from retrofit %s", t.getLocalizedMessage());
    }

    private boolean refreshMovieList() {
        long last = lastCall;
        long now = System.currentTimeMillis();
        lastCall = now;
        if (now - last < FRESH_TIMEOUT_IN_MINUTES) {
            return false;
        }
        return true;
    }
}
