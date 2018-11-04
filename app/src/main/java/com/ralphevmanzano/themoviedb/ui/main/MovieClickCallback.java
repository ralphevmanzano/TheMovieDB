package com.ralphevmanzano.themoviedb.ui.main;

import android.view.View;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;

public interface MovieClickCallback {
    void onMovieClicked(MinimizedMovie movie,View rootView, View sharedView);
}
