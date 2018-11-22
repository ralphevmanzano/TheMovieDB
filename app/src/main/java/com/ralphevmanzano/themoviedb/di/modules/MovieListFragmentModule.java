package com.ralphevmanzano.themoviedb.di.modules;

import com.ralphevmanzano.themoviedb.ui.adapters.HomeAdapter;
import com.ralphevmanzano.themoviedb.ui.main.MainActivity;
import com.ralphevmanzano.themoviedb.ui.main.MovieListFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieListFragmentModule {

    @Provides
    HomeAdapter provideMovieAdapter(MovieListFragment fragment){
        return new HomeAdapter(fragment);
    }
}