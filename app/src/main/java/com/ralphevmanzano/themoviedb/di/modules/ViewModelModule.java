package com.ralphevmanzano.themoviedb.di.modules;

import com.ralphevmanzano.themoviedb.di.ViewModelKey;
import com.ralphevmanzano.themoviedb.viewmodels.MovieListViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.MovieViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindsMovieListViewModel(MovieListViewModel movieListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(MovieViewModelFactory movieViewModelFactory);
}
