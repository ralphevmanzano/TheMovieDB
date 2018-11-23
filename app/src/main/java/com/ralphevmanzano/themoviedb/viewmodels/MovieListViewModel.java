package com.ralphevmanzano.themoviedb.viewmodels;

import com.ralphevmanzano.themoviedb.data.MovieRepo;
import com.ralphevmanzano.themoviedb.data.Resource;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.utils.SchedulersFacade;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MovieListViewModel extends ViewModel {

    private MovieRepo movieRepo;
    private SchedulersFacade schedulersFacade;

    private final MutableLiveData<Resource<MovieCollection>> movieList = new MutableLiveData<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    MovieListViewModel(MovieRepo movieRepo, SchedulersFacade schedulersFacade) {
        this.movieRepo = movieRepo;
        this.schedulersFacade = schedulersFacade;
        loadMovies();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    public LiveData<Resource<MovieCollection>> getMovies() {
        return movieList;
    }

    private void loadMovies() {
        disposable.add(movieRepo.loadCollectionMovies()
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
