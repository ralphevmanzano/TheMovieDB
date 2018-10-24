package com.ralphevmanzano.themoviedb.ui.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.Movie;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.adapters.MoviesAdapter;
import com.ralphevmanzano.themoviedb.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class MovieListFragment extends BaseFragment<MovieListViewModel> {

    @BindView(R.id.rvMovies)
    RecyclerView recyclerView;

    @Inject
    public MoviesAdapter moviesAdapter;

    List<Movie> movieList = new ArrayList<>();

    public static MovieListFragment newInstance() {
        Bundle args = new Bundle();
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        assert view != null;
        ButterKnife.bind(this, view);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(moviesAdapter);

    }

    private void getMovieList() {
        Timber.d("getMovies start");
        viewModel.loadMovies();
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            Timber.d("onChanged called");
            moviesAdapter.setData(movies);
        });
    }
}
