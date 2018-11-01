package com.ralphevmanzano.themoviedb.viewmodels;

import com.ralphevmanzano.themoviedb.data.MovieRepository;
import com.ralphevmanzano.themoviedb.data.Resource;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.utils.SchedulersFacade;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private SchedulersFacade schedulersFacade;

    private final MutableLiveData<Resource<MovieCollection>> movieList = new MutableLiveData<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    MovieListViewModel(MovieRepository movieRepository, SchedulersFacade schedulersFacade) {
        this.movieRepository = movieRepository;
        this.schedulersFacade = schedulersFacade;
        loadMovies();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    public MutableLiveData<Resource<MovieCollection>> getMovies() {
        return movieList;
    }

    private void loadMovies() {
        disposable.add(movieRepository.loadCollectionMovies()
                                      .observeOn(schedulersFacade.ui())
                                      .subscribeOn(schedulersFacade.io())
                                      .doOnNext(movieCollectionResource -> {
                                          assert movieCollectionResource.data != null;
                                          Timber.d("load movies size %d", movieCollectionResource.data.getTotalMovies());
                                          movieList.setValue(movieCollectionResource);
                                      })
                                      .doOnError(Throwable::printStackTrace)
                                      .subscribe());
    }
}
