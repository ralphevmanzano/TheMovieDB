package com.ralphevmanzano.themoviedb.ui.main;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.HomeData;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.databinding.FragmentHomeBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.adapters.HomeAdapter;
import com.ralphevmanzano.themoviedb.ui.details.MovieDetailsFragment;
import com.ralphevmanzano.themoviedb.viewmodels.MovieListViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.SharedViewModel;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.TransitionInflater;
import timber.log.Timber;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class MovieListFragment extends BaseFragment<MovieListViewModel, FragmentHomeBinding> implements MovieClickCallback {

    private SharedViewModel<Movie> sharedViewModel;

    @Inject
    HomeAdapter adapter;

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

    @Override
    public void onResume() {
        super.onResume();
        int bgColor = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark);
        Objects.requireNonNull(getActivity()).getWindow().setStatusBarColor(bgColor);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Toolbar toolbar = binding.toolbar;
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        setupViews();
//        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMovieList();
    }

    private void setupViews() {
        if (getActivity() != null)
            ActivityCompat.postponeEnterTransition(getActivity());

        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvMovies.setAdapter(adapter);
        binding.rvMovies.getViewTreeObserver().addOnPreDrawListener(() -> {
            if (getActivity() != null)
                ActivityCompat.startPostponedEnterTransition(getActivity());
            return true;
        });
    }

    private void getMovieList() {
        Timber.d("getMoviesList");
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            binding.setResource(movies);
        });
    }

    @Override
    public void onMovieClicked(Movie movie, View rootView, View sharedView) {
        sharedViewModel.select(movie);

        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movie.getPosterPath(), ViewCompat.getTransitionName(sharedView));

        setExitTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.no_transition));
        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.shared_element_transition));
        fragment.setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.shared_element_transition));
        fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.no_transition));
//        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));

        Objects.requireNonNull(getActivity()).getSupportFragmentManager()
               .beginTransaction()
               .addSharedElement(sharedView, Objects.requireNonNull(ViewCompat.getTransitionName(sharedView)))
               .replace(R.id.container, fragment)
               .addToBackStack(null)
               .commit();
    }
}
