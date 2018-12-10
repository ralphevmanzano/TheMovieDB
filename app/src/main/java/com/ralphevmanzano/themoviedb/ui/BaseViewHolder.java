package com.ralphevmanzano.themoviedb.ui;

import com.ralphevmanzano.themoviedb.BR;
import com.ralphevmanzano.themoviedb.databinding.ItemMovieBinding;
import com.ralphevmanzano.themoviedb.ui.main.MovieClickCallback;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding, @Nullable MovieClickCallback clickCallback) {
        super(binding.getRoot());
        this.binding = binding;
        if (clickCallback != null)
            binding.getRoot().setOnClickListener(view -> {
                if (binding instanceof ItemMovieBinding)
                    clickCallback.onMovieClicked(((ItemMovieBinding) binding).getMovie(),
                                                binding.getRoot(),
                                                ((ItemMovieBinding) binding).ivMovie);
            });
    }

    public void onBind(T data) {
        binding.setVariable(BR.movie, data);
        binding.executePendingBindings();
    }
}