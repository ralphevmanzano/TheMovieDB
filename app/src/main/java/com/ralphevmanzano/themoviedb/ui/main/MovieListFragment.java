package com.ralphevmanzano.themoviedb.ui.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.HomeData;
import com.ralphevmanzano.themoviedb.databinding.FragmentHomeBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.adapters.HomeAdapter;
import com.ralphevmanzano.themoviedb.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import timber.log.Timber;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class MovieListFragment extends BaseFragment<MovieListViewModel, FragmentHomeBinding> {


    @Inject
    HomeAdapter moviesAdapter;

    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public Class<MovieListViewModel> getViewModel() {
        return MovieListViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMovieList();
    }

    private void setupViews() {
        Log.d("MovieListFragment", "setupViews: ");
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvMovies.setAdapter(moviesAdapter);

    }

    private void getMovieList() {
        Timber.d("getMoviesList");
        List<HomeData> homeData = new ArrayList<>();
        homeData.add(new HomeData(HomeData.HEADER, "Discover"));
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            Timber.d("onChanged called %s", movies.size());
            homeData.add(new HomeData(HomeData.MOVIE_LIST, movies));
            moviesAdapter.setData(homeData);
        });
    }
}
