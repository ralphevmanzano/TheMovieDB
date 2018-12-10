package com.ralphevmanzano.themoviedb.viewmodels;

import com.ralphevmanzano.themoviedb.data.SuggestionsRepo;
import com.ralphevmanzano.themoviedb.utils.SchedulersFacade;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class SuggestionsViewModel extends ViewModel {

    private SuggestionsRepo suggestionsRepo;
    private SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public SuggestionsViewModel(SuggestionsRepo suggestionsRepo, SchedulersFacade schedulersFacade) {
        this.suggestionsRepo = suggestionsRepo;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
