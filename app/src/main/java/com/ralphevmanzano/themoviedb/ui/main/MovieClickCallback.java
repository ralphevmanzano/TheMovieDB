package com.ralphevmanzano.themoviedb.ui.main;

import android.view.View;

import com.ralphevmanzano.themoviedb.data.models.Movie;

public interface MovieClickCallback {
    void onMovieClicked(Movie movie, View sharedView);
}
