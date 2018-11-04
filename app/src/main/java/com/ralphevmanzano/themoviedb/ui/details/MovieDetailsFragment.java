package com.ralphevmanzano.themoviedb.ui.details;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.databinding.FragmentMovieDetailsBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.viewmodels.MovieDetailsViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.SharedViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding> {

    private SharedViewModel<MinimizedMovie> sharedViewModel;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public Class<MovieDetailsViewModel> getViewModel() {
        return MovieDetailsViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_movie_details;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.tv);
        assert getArguments() != null;
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), minimizedMovie -> {
            textView.setText(String.valueOf(minimizedMovie.getId()));
        });

    }
}
