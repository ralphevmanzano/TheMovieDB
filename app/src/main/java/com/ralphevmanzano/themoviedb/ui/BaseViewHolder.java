package com.ralphevmanzano.themoviedb.ui;

import com.ralphevmanzano.themoviedb.BR;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(T data) {
        binding.setVariable(BR.minimizedMovie, data);
        binding.executePendingBindings();
    }
}