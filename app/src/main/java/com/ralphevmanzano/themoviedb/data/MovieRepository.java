package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.local.dao.MovieDao;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;
import com.ralphevmanzano.themoviedb.data.remote.model.MovieResponse;
import com.ralphevmanzano.themoviedb.utils.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        return Flowable.zip(loadMovies(Movie.NOW_PLAYING, Constants.API_NOW_PLAYING),
                loadMovies(Movie.POPULAR, Constants.API_POPULAR),
                loadMovies(Movie.TOP_RATED, Constants.API_TOP_RATED),
                loadMovies(Movie.UPCOMING, Constants.API_UPCOMING),
                (np, pop, top, up) -> {
                    assert np.data != null;
                    Timber.d("Now playing movies size collection: %d", np.data.size());

                    assert pop.data != null;
                    Timber.d("Popular movies size collection: %d", pop.data.size());

                    assert top.data != null;
                    Timber.d("Top rated movies size collection: %d", top.data.size());

                    assert up.data != null;
                    Timber.d("Upcoming movies size collection: %d", up.data.size());

                    return Resource.success(new MovieCollection(np.data, pop.data, top.data, up.data));
                });
    }

    public Flowable<Resource<List<MinimizedMovie>>> loadMovies(int category, String apiCategory) {
        return new NetworkBoundResource<List<MinimizedMovie>, MovieResponse>() {

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
            protected Flowable<List<MinimizedMovie>> loadFromDb() {
                if (category == Movie.NOW_PLAYING) {
                    return movieDao.getNowPlayingMovies(String.valueOf(category));
                }
                return movieDao.getMiniminzedMovies(String.valueOf(category));
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
