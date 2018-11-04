package com.ralphevmanzano.themoviedb.databinding;

import com.ralphevmanzano.themoviedb.data.Resource;
import com.ralphevmanzano.themoviedb.data.models.HomeData;
import com.ralphevmanzano.themoviedb.data.models.MovieCollection;
import com.ralphevmanzano.themoviedb.ui.adapters.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public final class MovieListBindingAdapter {
    @BindingAdapter("resource")
    public static void setResource(RecyclerView recyclerView, Resource resource) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null)
            return;
        if (resource == null || resource.data == null)
            return;
        if (adapter instanceof HomeAdapter) {
            if (resource.data instanceof MovieCollection) {
                List<HomeData> homeData = new ArrayList<>();
                homeData.add(new HomeData(HomeData.LATEST, ((MovieCollection) resource.data).getNowPlayingMovies().get(0)));
                homeData.add(new HomeData(HomeData.HEADER, "Now Playing"));
                homeData.add(new HomeData(HomeData.MOVIE_LIST, ((MovieCollection) resource.data).getNowPlayingMovies()));
                homeData.add(new HomeData(HomeData.HEADER, "Popular"));
                homeData.add(new HomeData(HomeData.MOVIE_LIST, ((MovieCollection) resource.data).getPopularMovies()));
                homeData.add(new HomeData(HomeData.HEADER, "Top Rated"));
                homeData.add(new HomeData(HomeData.MOVIE_LIST, ((MovieCollection) resource.data).getTopRatedMovies()));
                homeData.add(new HomeData(HomeData.HEADER, "Upcoming"));
                homeData.add(new HomeData(HomeData.MOVIE_LIST, ((MovieCollection) resource.data).getUpcomingMovies()));
                ((HomeAdapter) adapter).setData(homeData);
            }
        }
    }
}
