package com.ralphevmanzano.themoviedb.ui.details;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.databinding.FragmentMovieDetailsBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.main.MainActivity;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.ralphevmanzano.themoviedb.viewmodels.MovieDetailsViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.SharedViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding> {

    private static final String KEY_IMG_URL = MovieDetailsFragment.class.getSimpleName() + "_IMG_URL";
    private static final String KEY_IMG_TRANSITION_NAME = MovieDetailsFragment.class.getSimpleName() + "_IMG_TRANS_NAME";

    private SharedViewModel<MinimizedMovie> sharedViewModel;
    private MovieDetails movieDetails;
    private String imgUrl;
    private String transitionName;

    public static MovieDetailsFragment newInstance(String imgUrl, String transitionName) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        if (!TextUtils.isEmpty(imgUrl) && !TextUtils.isEmpty(transitionName)) {
            args.putString(KEY_IMG_URL, imgUrl);
            args.putString(KEY_IMG_TRANSITION_NAME, transitionName);
        }
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

        imgUrl = bundle != null ? bundle.getString(KEY_IMG_URL) : "";
        transitionName = bundle != null ? bundle.getString(KEY_IMG_TRANSITION_NAME) : "";

        if (getActivity() != null)
            ActivityCompat.postponeEnterTransition(getActivity());
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

        if (!TextUtils.isEmpty(transitionName))
            ViewCompat.setTransitionName(binding.included.imgMovieDetails, transitionName);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(),
                minimizedMovie -> {
                    getMovieDetails(minimizedMovie.getId());
                    binding.setMinMovie(minimizedMovie);
                    setBgImage(minimizedMovie.getBackdropPath());
                    getMovieVideo(minimizedMovie.getId());
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;

        ImageView imgMovie = binding.included.imgMovieDetails;

        binding.collapsingToolbar.setTitle(" ");
        binding.toolbar.setNavigationOnClickListener(view1 -> Objects.requireNonNull(getActivity()).onBackPressed());

        if (!TextUtils.isEmpty(imgUrl)) {
            Picasso.get()
                   .load(Constants.IMAGE_ENDPOINT_PREFIX + imgUrl)
                   .noFade()
                   .fit()
                   .into(imgMovie, new Callback() {
                       @Override
                       public void onSuccess() {
                           if (getActivity() != null)
                               ActivityCompat.startPostponedEnterTransition(getActivity());
                       }

                       @Override
                       public void onError(Exception e) {
                           if (getActivity() != null)
                               ActivityCompat.startPostponedEnterTransition(getActivity());
                       }
                   });
        }

        setupListeners(imgMovie);
    }

    private void setupListeners(ImageView imgMovie) {
        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float temp = -verticalOffset / (float) appBarLayout.getTotalScrollRange();
            imgMovie.setPivotY(0);
            imgMovie.setPivotX(imgMovie.getMeasuredWidth() / 2);
            imgMovie.setScaleX((float) (1 - (0.5 * temp)));
            imgMovie.setScaleY((float) (1 - (0.5 * temp)));
            binding.included.btnFave.setTranslationY(-190 * temp);
            binding.included.btnTrailer.setTranslationY(-190 * temp);
            binding.included.contentDetails.setTranslationY(verticalOffset / 2);
            binding.blurView.setAlpha(temp);
        });

        binding.included.scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (isTitleVisible()) {
                if (!TextUtils.isEmpty(Objects.requireNonNull(binding.collapsingToolbar.getTitle()).toString().trim())) {
                    binding.collapsingToolbar.setTitle(" ");
                }
            } else {
                if (TextUtils.isEmpty(Objects.requireNonNull(binding.collapsingToolbar.getTitle()).toString().trim())) {
                    if (movieDetails != null)
                        binding.collapsingToolbar.setTitle(movieDetails.getTitle());

                }
            }
        });
    }

    private void getMovieDetails(long id) {
        viewModel.getMovieDetails(id).observe(getViewLifecycleOwner(), movieDetailsResource -> {
            this.movieDetails = movieDetailsResource.data;
            binding.included.setMovieDetails(movieDetails);
            binding.executePendingBindings();
        });
    }

    private void getMovieVideo(long id) {
        viewModel.getMovieVideo(id).observe(getViewLifecycleOwner(), videoResource -> {
            Timber.d("movie video status %s", videoResource.status);
            //TODO: set play trailer button url
        });
    }

    private void setToolbarColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(palette -> {
            Palette.Swatch darkSwatch = palette != null ? palette.getDarkVibrantSwatch() : null;
            if (getContext() != null && getActivity() != null) {
                int bgColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);

                if (darkSwatch != null)
                    bgColor = darkSwatch.getRgb();

                binding.collapsingToolbar.setContentScrimColor(bgColor);
                getActivity().getWindow().setStatusBarColor(bgColor);
            }
        });
    }

    private void setBgImage(String imgUrl) {
        Picasso.get()
               .load(Constants.BACKDROP_ENDPOINT_PREFIX + imgUrl)
               .resize(1080, 608)
               .placeholder(R.color.colorPrimary)
               .into(binding.imgBg, new Callback() {
                   @Override
                   public void onSuccess() {
                       Bitmap bitmap = ((BitmapDrawable) binding.imgBg.getDrawable()).getBitmap();
                       setToolbarColor(bitmap);
                   }

                   @Override
                   public void onError(Exception e) {

                   }
               });
    }

    private boolean isTitleVisible() {
        Rect scrollBounds = new Rect();
        binding.included.scrollView.getHitRect(scrollBounds);
        return binding.included.txtTitle.getLocalVisibleRect(scrollBounds);
    }
}
