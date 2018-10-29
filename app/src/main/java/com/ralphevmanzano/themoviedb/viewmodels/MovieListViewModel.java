package com.ralphevmanzano.themoviedb.viewmodels;

import com.ralphevmanzano.themoviedb.data.MovieRepository;
import com.ralphevmanzano.themoviedb.data.models.Movie;
import com.ralphevmanzano.themoviedb.utils.SchedulersFacade;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private SchedulersFacade schedulersFacade;

    private final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

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

    public MutableLiveData<List<Movie>> getMovies() {
        return movieList;
    }

    private void loadMovies() {
        disposable.add(movieRepository.getMovieList()
                                      .observeOn(schedulersFacade.ui())
                                      .subscribeOn(schedulersFacade.io())
                                      .subscribe(movieList::setValue, Timber::e));
    }
}
