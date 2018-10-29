package com.ralphevmanzano.themoviedb.utils;

import android.os.Bundle;

import com.ralphevmanzano.themoviedb.data.models.Movie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class MovieDiffCallback extends DiffUtil.ItemCallback<Movie> {

    @Override
    public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return oldItem.equals(newItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull Movie oldItem, @NonNull Movie newItem) {
        Bundle diffBundle = new Bundle();

        if (newItem.getVoteAverage() != oldItem.getVoteAverage()) {
            diffBundle.putDouble(Movie.KEY_VOTE_AVERAGE, newItem.getVoteAverage());
        }
        if (newItem.getVoteCount() != oldItem.getVoteCount()) {
            diffBundle.putDouble(Movie.KEY_VOTE_COUNT, newItem.getVoteCount());
        }
        if (diffBundle.size() == 0) return null;
        return diffBundle;
    }
}
