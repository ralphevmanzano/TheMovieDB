package com.ralphevmanzano.themoviedb.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ralphevmanzano.themoviedb.ui.main.MovieClickCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T> extends ListAdapter<T, BaseViewHolder<T>> {

    private MovieClickCallback clickCallback;
    protected ViewDataBinding binding;

    protected BaseAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback, @Nullable MovieClickCallback clickCallback) {
        super(diffCallback);
        if (clickCallback != null)
            this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return new BaseViewHolder<T>(binding, clickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        holder.onBind(getItem(holder.getAdapterPosition()));
    }
}
