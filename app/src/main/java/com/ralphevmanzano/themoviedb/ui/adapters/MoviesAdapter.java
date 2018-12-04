package com.ralphevmanzano.themoviedb.ui.adapters;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.databinding.ItemMovieBinding;
import com.ralphevmanzano.themoviedb.ui.BaseAdapter;
import com.ralphevmanzano.themoviedb.ui.BaseViewHolder;
import com.ralphevmanzano.themoviedb.ui.main.MovieClickCallback;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import timber.log.Timber;

public class MoviesAdapter extends BaseAdapter<Movie> {

    MoviesAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, MovieClickCallback clickCallback) {
        super(diffCallback, clickCallback);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_movie;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<Movie> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (binding instanceof ItemMovieBinding) {
            ViewCompat.setTransitionName(((ItemMovieBinding) binding).ivMovie,
                    String.format(Locale.ENGLISH, "%s%d", ((ItemMovieBinding) binding).getMinimizedMovie().getPosterPath(), position));
        }
    }
}
