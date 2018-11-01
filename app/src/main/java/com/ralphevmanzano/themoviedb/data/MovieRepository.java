package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.local.dao.MovieDao;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;
import com.ralphevmanzano.themoviedb.data.remote.model.MovieResponse;
import com.ralphevmanzano.themoviedb.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Flowable;
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

    public Flowable<Resource<MovieCollection>> loadCollectionMovies() {
        return Flowable.zip(loadUpcomingMovies(), loadPopularMovies(), (nowPlayingMovies, popularMovies) -> {
            assert nowPlayingMovies.data != null;
            Timber.d("Upcoming movies size collection: %d", nowPlayingMovies.data.size());

            assert popularMovies.data != null;
            Timber.d("Popular movies size collection: %d", popularMovies.data.size());

            return Resource.success(new MovieCollection(nowPlayingMovies.data, popularMovies.data));
        });
    }

    private Flowable<Resource<List<Movie>>> loadPopularMovies() {
        Timber.d("Loading popular movies");
        return new NetworkBoundResource<List<Movie>, MovieResponse>() {

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @Override
            protected void saveCallResult(MovieResponse item) {
                if (item != null) {
                    for (Movie movie : item.getMovies()) {
                        movie.setCategory(Movie.POPULAR);
                    }
                    movieDao.insertMovies(item.getMovies());
                    Timber.d("Popular movies size: %d", item.getMovies().size());
                }
            }

            @NonNull
            @Override
            protected Flowable<List<Movie>> loadFromDb() {
                return movieDao.getPopularMovies();
            }

            @NonNull
            @Override
            protected Flowable<MovieResponse> createCall() {
                return movieDBService.getPopularMovies(Constants.API_KEY);
            }
        }.toFlowable();
    }

    private Flowable<Resource<List<Movie>>> loadUpcomingMovies() {
        Timber.d("Loading Upcoming movies");
        return new NetworkBoundResource<List<Movie>, MovieResponse>() {

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @Override
            protected void saveCallResult(MovieResponse item) {
                if (item != null) {
                    for (Movie movie : item.getMovies()) {
                        movie.setCategory(Movie.UPCOMING);
                    }
                    movieDao.insertMovies(item.getMovies());
                    Timber.d("Upcoming movies size: %d", item.getMovies().size());
                }
            }

            @NonNull
            @Override
            protected Flowable<List<Movie>> loadFromDb() {
                return movieDao.getUpcomingMovies();
            }

            @NonNull
            @Override
            protected Flowable<MovieResponse> createCall() {
                return movieDBService.getUpcomingMovies(Constants.API_KEY);
            }
        }.toFlowable();
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
