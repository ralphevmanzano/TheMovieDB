package com.ralphevmanzano.themoviedb.utils;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersFacade {

    @Inject
    public SchedulersFacade() {
    }

    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }

    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

}
