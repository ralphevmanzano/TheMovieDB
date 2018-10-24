package com.ralphevmanzano.themoviedb.ui.main;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<Type extends RecyclerView.ViewHolder, Data> extends RecyclerView.Adapter<Type>  {
    public abstract void setData(List<Data> data);
}
