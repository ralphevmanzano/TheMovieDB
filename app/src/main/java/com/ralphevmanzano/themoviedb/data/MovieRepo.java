package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.local.dao.MovieDao;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.data.models.MovieResponse;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;
import com.ralphevmanzano.themoviedb.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import timber.log.Timber;

public class MovieRepo {
    private static final String TAG = "MovieRepo";
    private static final long FRESH_TIMEOUT_IN_MINUTES = 1 * 1000 * 60;
    private long lastCall = 0;
    private MovieDao movieDao;
    private MovieDBService movieDBService;

    @Inject
    MovieRepo(MovieDBService movieDBService, MovieDao movieDao) {
        this.movieDBService = movieDBService;
        this.movieDao = movieDao;
    }

    //TODO: try merge, tapos mag create ug tig organize sa whole list of merged Movies
    public Flowable<Resource<MovieCollection>> loadCollectionMovies() {
        return Flowable.zip(loadMovies(Movie.NOW_PLAYING, Constants.API_NOW_PLAYING),
                loadMovies(Movie.POPULAR, Constants.API_POPULAR),
                loadMovies(Movie.TOP_RATED, Constants.API_TOP_RATED),
                loadMovies(Movie.UPCOMING, Constants.API_UPCOMING),
                (np, pop, top, up) -> {
                    assert np.data != null;
                    Timber.d("Now playing movies: %s %d", np.status, np.data.size());

                    assert pop.data != null;
                    Timber.d("Popular movies: %s %d", pop.status, pop.data.size());

                    assert top.data != null;
                    Timber.d("Top rated movies: %s %d", top.status, top.data.size());

                    assert up.data != null;
                    Timber.d("Upcoming movies: %s %d", up.status, up.data.size());

                    return Resource.success(new MovieCollection(np.data, pop.data, top.data, up.data));
                });
    }

    public Flowable<Resource<List<Movie>>> loadMovies(int category, String apiCategory) {
        return new NetworkBoundResource<List<Movie>, MovieResponse>() {

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @Override
            protected void saveCallResult(MovieResponse item) {
                if (item != null) {
                    for (Movie movie : item.getMovies()) {
                        movie.setCategory(String.valueOf(category));
                    }
                    movieDao.upsert(item.getMovies());
                    Timber.d("save call movies size: %d", item.getMovies().size());
                }
            }

            @NonNull
            @Override
            protected Flowable<List<Movie>> loadFromDb() {
                if (category == Movie.NOW_PLAYING) {
                    return movieDao.getNowPlayingMovies(String.valueOf(category));
                }
                return movieDao.getMoviesByCategory(String.valueOf(category));
            }

            @NonNull
            @Override
            protected Flowable<MovieResponse> createCall() {
                return movieDBService.getMovies(apiCategory, Constants.API_KEY);
            }
        }.toFlowable();
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
