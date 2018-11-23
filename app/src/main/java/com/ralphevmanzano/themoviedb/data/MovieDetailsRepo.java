package com.ralphevmanzano.themoviedb.data;

import com.ralphevmanzano.themoviedb.data.local.MovieDatabase;
import com.ralphevmanzano.themoviedb.data.local.dao.MovieDetailsDao;
import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.remote.MovieDBService;
import com.ralphevmanzano.themoviedb.utils.Constants;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailsRepo {

    private MovieDBService movieDBService;
    private MovieDetailsDao movieDetailsDao;

    @Inject
    public MovieDetailsRepo(MovieDBService movieDBService, MovieDetailsDao movieDetailsDao) {
        this.movieDBService = movieDBService;
        this.movieDetailsDao = movieDetailsDao;
    }

//    public Flowable<Resource<MovieDetails>> loadMovieDetailss(long id) {
//        return movieDBService.getMovieDetails(id, Constants.API_KEY)
//                             .subscribeOn(Schedulers.io())
//                             .doOnNext(this::handleResult)
//                             .flatMap(apiResponse -> Timber.d(""));
//    }

    private void handleResult(MovieDetails movieDetails) {
        if (movieDetails != null) {
            movieDetailsDao.insert(movieDetails);
        }
    }

    public Flowable<Resource<MovieDetails>> loadMovieDetails(long id) {
        return new NetworkBoundResource<MovieDetails, MovieDetails>() {
            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @Override
            protected void saveCallResult(MovieDetails item) {
                if (item != null) {
                    movieDetailsDao.insert(item);
                }
            }

            @NonNull
            @Override
            protected Flowable<MovieDetails> loadFromDb() {
                return movieDetailsDao.getMovieDetails(id);
            }

            @NonNull
            @Override
            protected Flowable<MovieDetails> createCall() {
                return movieDBService.getMovieDetails(id, Constants.API_KEY);
            }
        }.toFlowable();
    }
}
