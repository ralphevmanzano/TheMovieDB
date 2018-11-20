package com.ralphevmanzano.themoviedb;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.ralphevmanzano.themoviedb.di.DaggerAppComponent;

import javax.inject.Inject;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        DaggerAppComponent.builder()
                          .application(this)
                          .build()
                          .inject(this);

        Stetho.initializeWithDefaults(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
