package com.ralphevmanzano.themoviedb.ui;

import com.ralphevmanzano.themoviedb.data.models.HomeData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHomeAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public abstract void setData(List<HomeData> data);

}
