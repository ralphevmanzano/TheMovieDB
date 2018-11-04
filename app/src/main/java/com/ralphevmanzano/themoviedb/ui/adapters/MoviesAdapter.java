package com.ralphevmanzano.themoviedb.ui.adapters;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.ui.BaseAdapter;
import com.ralphevmanzano.themoviedb.ui.main.MovieClickCallback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import timber.log.Timber;

public class MoviesAdapter extends BaseAdapter<Movie> {

    MoviesAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, MovieClickCallback clickCallback) {
        super(diffCallback, clickCallback);
        Timber.d("Movies Adapter");
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_movie;
    }
}
