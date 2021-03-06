package com.ralphevmanzano.themoviedb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ralphevmanzano.themoviedb.BR;
import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.HomeData;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.databinding.ItemMovieListBinding;
import com.ralphevmanzano.themoviedb.ui.BaseHomeAdapter;
import com.ralphevmanzano.themoviedb.ui.main.MovieClickCallback;
import com.ralphevmanzano.themoviedb.utils.MovieDiffCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class HomeAdapter extends BaseHomeAdapter<HomeAdapter.HomeViewHolder> {

    private List<HomeData> movieList;
    private MovieClickCallback clickCallback;
    int[] leftOffset = new int[9];
    int[] pos = new int[9];

    public HomeAdapter(MovieClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public void setData(List<HomeData> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (movieList != null && movieList.size() > 0)
            return movieList.size();
        return 0;
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new HomeViewHolder(parent.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        holder.onBind(movieList.get(holder.getAdapterPosition()));

        if (holder.binding instanceof ItemMovieListBinding) {
            final ItemMovieListBinding binding = ((ItemMovieListBinding) holder.binding);
            if (holder.getAdapterPosition() != -1 && binding.rvMovieList.getLayoutManager() instanceof LinearLayoutManager)
                ((LinearLayoutManager) binding.rvMovieList.getLayoutManager())
                        .scrollToPositionWithOffset(this.pos[pos], leftOffset[pos]);
        }
    }

    @Override
    public void onViewRecycled(@NonNull HomeViewHolder holder) {
        super.onViewRecycled(holder);
        final int position = holder.getAdapterPosition();
        if (position != -1) {
            if (holder.binding instanceof ItemMovieListBinding) {
                final ItemMovieListBinding binding = ((ItemMovieListBinding) holder.binding);
                final RecyclerView.LayoutManager layoutManager = binding.rvMovieList.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    final View item = binding.rvMovieList.getChildAt(0);
                    pos[position] = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    leftOffset[position] = (item == null) ? 0 : (item.getLeft() - binding.rvMovieList.getPaddingLeft());
                } else {
                    pos[position] = 0;
                    leftOffset[position] = 0;
                }
                for (int i : this.pos) {
                    Timber.d("pos %d", i);
                }
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (movieList.get(position).getViewType()) {
            case HomeData.HEADER:
                return R.layout.item_movie_label;
            case HomeData.MOVIE_LIST:
                return R.layout.item_movie_list;
            case HomeData.LATEST:
                return R.layout.item_movie_latest;
        }
        return 0;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;
        Context context;
        LinearLayoutManager layoutManager;

        HomeViewHolder(Context context, ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        @SuppressWarnings("unchecked")
        void onBind(HomeData data) {
            binding.setVariable(BR.homedata, data);
            binding.executePendingBindings();

            if (binding instanceof ItemMovieListBinding) {
                MoviesAdapter adapter = new MoviesAdapter(new MovieDiffCallback(), clickCallback);
                layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                ((ItemMovieListBinding) binding).rvMovieList.setLayoutManager(layoutManager);
                ((ItemMovieListBinding) binding).rvMovieList.setAdapter(adapter);
                adapter.submitList((List<Movie>) data.getData());
            }
        }
    }
}
