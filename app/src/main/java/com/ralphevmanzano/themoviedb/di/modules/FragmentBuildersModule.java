package com.ralphevmanzano.themoviedb.di.modules;

import com.ralphevmanzano.themoviedb.ui.main.MovieListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract MovieListFragment contributeMovieListFragment();
}
