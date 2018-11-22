package com.ralphevmanzano.themoviedb.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.databinding.FragmentHomeBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.adapters.HomeAdapter;
import com.ralphevmanzano.themoviedb.viewmodels.MovieListViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.SharedViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.TransitionInflater;
import timber.log.Timber;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class MovieListFragment extends BaseFragment<MovieListViewModel, FragmentHomeBinding> implements MovieClickCallback {

    private SharedViewModel<MinimizedMovie> sharedViewModel;

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
    @SuppressWarnings("unchecked")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        setupViews();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMovieList();
        postponeEnterTransition();
    }

    private void setupViews() {
        Timber.d("setupViews: ");
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeAdapter homeAdapter = new HomeAdapter(this);
        binding.rvMovies.setAdapter(homeAdapter);
        binding.rvMovies.getViewTreeObserver().addOnPreDrawListener(() -> {
            startPostponedEnterTransition();
            return true;
        });
    }

    private void getMovieList() {
        Timber.d("getMoviesList");
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            assert movies.data != null;
            Timber.d("status movies %s size %d", movies.status, movies.data.getTotalMovies());
            binding.setResource(movies);
        });
    }


    @Override
    public void onMovieClicked(MinimizedMovie movie, View rootView, View sharedView) {
        Toast.makeText(getContext(), String.valueOf(movie.getId()), Toast.LENGTH_SHORT).show();
        sharedViewModel.select(movie);
        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(sharedView, movie.getPosterPath())
                .build();
        Navigation.findNavController(rootView).navigate(R.id.movie_details_dest, null, null, extras);
    }
}
