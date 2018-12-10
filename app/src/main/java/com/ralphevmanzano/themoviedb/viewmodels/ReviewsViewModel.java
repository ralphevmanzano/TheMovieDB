package com.ralphevmanzano.themoviedb.viewmodels;

import com.ralphevmanzano.themoviedb.data.ReviewsRepo;
import com.ralphevmanzano.themoviedb.utils.SchedulersFacade;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class ReviewsViewModel extends ViewModel {

    private ReviewsRepo reviewsRepo;
    private SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ReviewsViewModel(ReviewsRepo reviewsRepo, SchedulersFacade schedulersFacade) {
        this.reviewsRepo = reviewsRepo;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
