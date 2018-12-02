package com.ralphevmanzano.themoviedb.ui.details;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.FullScreenHelper;
import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.databinding.FragmentMovieDetailsBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.main.MainActivity;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.ralphevmanzano.themoviedb.viewmodels.MovieDetailsViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.SharedViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.TransitionInflater;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding> {

    private SharedViewModel<MinimizedMovie> sharedViewModel;
    private String imgUrl;

    public static MovieDetailsFragment newInstance(String imgUrl) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putString("imgUrl", imgUrl);
        fragment.setArguments(args);

        return fragment;
    }

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (Objects.requireNonNull(bundle).containsKey("imgUrl")) {
            imgUrl = bundle.getString("imgUrl");
        }
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
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Toolbar toolbar = binding.toolbar;
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);


        sharedViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SharedViewModel.class);
        postponeEnterTransition();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;

        ImageView imgMovie = binding.included.imgMovieDetails;

        sharedViewModel.getSelected().observe(getViewLifecycleOwner(),
                minimizedMovie -> {
                    binding.setMinMovie(minimizedMovie);
                    getMovieDetails(minimizedMovie.getId());
                    getMovieVideo(minimizedMovie.getId());
        });

        binding.collapsingToolbar.setTitle(" ");
        binding.toolbar.setNavigationOnClickListener(view1 -> Objects.requireNonNull(getActivity()).onBackPressed());

        ViewCompat.setTransitionName(imgMovie, imgUrl);

        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float temp = -verticalOffset/(float)appBarLayout.getTotalScrollRange();
            imgMovie.setPivotY(0);
            imgMovie.setPivotX(imgMovie.getMeasuredWidth()/2);
            imgMovie.setScaleX((float) (1 - (0.5 * temp)));
            imgMovie.setScaleY((float) (1 - (0.5 * temp)));
            binding.included.btnFave.setTranslationY(verticalOffset/2);
            binding.included.btnTrailer.setTranslationY(verticalOffset/2);
            binding.included.contentDetails.setTranslationY(verticalOffset/2);
        });

        Picasso.get().load(Constants.IMAGE_ENDPOINT_PREFIX + imgUrl).into(imgMovie, new Callback() {
            @Override
            public void onSuccess() {
                startPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                startPostponedEnterTransition();
            }
        });
    }

    private void getMovieDetails(long id) {
        viewModel.getMovieDetails(id).observe(getViewLifecycleOwner(), movieDetailsResource -> {
            Timber.d("movie details status %s", movieDetailsResource.status);
            Timber.d("Movie details %s", Objects.requireNonNull(movieDetailsResource.data).getHomepage());
        });
    }

    private void getMovieVideo(long id) {
        viewModel.getMovieVideo(id).observe(getViewLifecycleOwner(), videoResource -> {
            Timber.d("movie video status %s", videoResource.status);
            //TODO: set play trailer button url
        });
    }
}
