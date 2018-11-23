package com.ralphevmanzano.themoviedb.viewmodels;

import com.ralphevmanzano.themoviedb.data.MovieDetailsRepo;
import com.ralphevmanzano.themoviedb.data.MovieRepo;
import com.ralphevmanzano.themoviedb.data.Resource;
import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.utils.SchedulersFacade;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MovieDetailsViewModel extends ViewModel {

    private MovieDetailsRepo movieDetailsRepo;
    private SchedulersFacade schedulersFacade;

    private final MutableLiveData<Resource<MovieDetails>> movieDetails = new MutableLiveData<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    @Inject
    public MovieDetailsViewModel(MovieDetailsRepo movieDetailsRepo, SchedulersFacade schedulersFacade) {
        this.movieDetailsRepo = movieDetailsRepo;
        this.schedulersFacade = schedulersFacade;
    }

    public LiveData<Resource<MovieDetails>> getMovieDetails(long id) {
        loadDetails(id);
        return movieDetails;
    }

    private void loadDetails(long id) {
        disposable.add(movieDetailsRepo.loadMovieDetails(id)
                                .observeOn(schedulersFacade.ui())
                                .subscribeOn(schedulersFacade.io())
                                .doOnNext(movieDetailsResource -> {
                                    Timber.d("load movie details %s", movieDetailsResource.data.getOriginalTitle());
                                    movieDetails.setValue(movieDetailsResource);
                                })
                                .doOnError(Throwable::printStackTrace)
                                .subscribe());
    }
}
