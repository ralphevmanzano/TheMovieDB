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

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavOptions;
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

    @Inject
    public HomeAdapter adapter;

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
        binding.rvMovies.setAdapter(adapter);
        binding.rvMovies.getViewTreeObserver().addOnPreDrawListener(() -> {
            startPostponedEnterTransition();
            return true;
        });
    }

    private void getMovieList() {
        Timber.d("getMoviesList");
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            Timber.d("===============================================");
            binding.setResource(movies);
        });
    }


    @Override
    public void onMovieClicked(MinimizedMovie movie, View rootView, View sharedView) {
        sharedViewModel.select(movie);
        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(sharedView, movie.getPosterPath())
                .build();
        Bundle bundle = new Bundle();
        bundle.putString("imgUrl", movie.getPosterPath());

        Navigation.findNavController(rootView).navigate(R.id.move_to_details, bundle, null, extras);
    }
}
