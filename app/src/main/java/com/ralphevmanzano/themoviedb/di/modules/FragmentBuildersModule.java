package com.ralphevmanzano.themoviedb.di.modules;

import com.ralphevmanzano.themoviedb.ui.details.MovieDetailsFragment;
import com.ralphevmanzano.themoviedb.ui.details.ReviewsFragment;
import com.ralphevmanzano.themoviedb.ui.details.SuggestionsFragment;
import com.ralphevmanzano.themoviedb.ui.main.MovieListFragment;
import com.ralphevmanzano.themoviedb.viewmodels.ReviewsViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = MovieListFragmentModule.class)
    abstract MovieListFragment contributeMovieListFragment();

    @ContributesAndroidInjector
    abstract MovieDetailsFragment contributeMovieDetailsFragment();

    @ContributesAndroidInjector
    abstract ReviewsFragment contributeReviewsFragment();

    @ContributesAndroidInjector
    abstract SuggestionsFragment contributeSuggestionsFragment();
}
