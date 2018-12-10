package com.ralphevmanzano.themoviedb.ui.details;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.models.Genre;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.databinding.FragmentMovieDetailsBinding;
import com.ralphevmanzano.themoviedb.ui.BaseFragment;
import com.ralphevmanzano.themoviedb.ui.adapters.DetailsViewPagerAdapter;
import com.ralphevmanzano.themoviedb.ui.main.MainActivity;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.ralphevmanzano.themoviedb.viewmodels.MovieDetailsViewModel;
import com.ralphevmanzano.themoviedb.viewmodels.SharedViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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
import androidx.viewpager.widget.ViewPager;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding> {

    private static final String KEY_IMG_URL = MovieDetailsFragment.class.getSimpleName() + "_IMG_URL";
    private static final String KEY_IMG_TRANSITION_NAME = MovieDetailsFragment.class.getSimpleName() + "_IMG_TRANS_NAME";

    private SharedViewModel<Movie> sharedViewModel;
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

        setupViewPager();

        if (!TextUtils.isEmpty(transitionName))
            ViewCompat.setTransitionName(binding.included.imgMovieDetails, transitionName);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(),
                movie -> {
                    getMovieDetails(movie.getId());
                    binding.setMovie(movie);
                    setBgImage(movie.getBackdropPath());
                    setRating(movie.getVoteAverage());
                    getMovieVideo(movie.getId());
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
            binding.included.btnFave.setTranslationY(-195 * temp);
            binding.included.btnTrailer.setTranslationY(-195 * temp);
            binding.included.contentDetails.setTranslationY(-195 * temp);
            binding.blurView.setAlpha(temp);
        });

        binding.included.scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (isTitleVisible()) {
                if (!TextUtils.isEmpty(Objects.requireNonNull(binding.collapsingToolbar.getTitle()).toString().trim())) {
                    binding.collapsingToolbar.setTitle(" ");
                    binding.toolbar.setSubtitle(" ");
                }
            } else {
                if (TextUtils.isEmpty(Objects.requireNonNull(binding.collapsingToolbar.getTitle()).toString().trim())) {
                    if (movieDetails != null) {
                        binding.collapsingToolbar.setTitle(movieDetails.getTitle());
                        binding.toolbar.setSubtitle(String.format(Locale.ENGLISH, "%s votes", NumberFormat.getNumberInstance(Locale.US).format(movieDetails.getVoteCount())));
                    }
                }
            }
        });
    }

    private void getMovieDetails(long id) {
        viewModel.getMovieDetails(id).observe(getViewLifecycleOwner(), movieDetailsResource -> {
            this.movieDetails = movieDetailsResource.data;
//            binding.included.setMovieDetails(movieDetails);
            binding.included.setResource(movieDetailsResource);
            setupFlexbox();
            if (movieDetails.getGenres() != null && movieDetails.getGenres().size() > 0) {
                Timber.d("genre %s", movieDetails.getGenres().get(0).getName());
            }
            binding.executePendingBindings();
        });
    }

    private void getMovieVideo(long id) {
        viewModel.getMovieVideo(id).observe(getViewLifecycleOwner(), videoResource -> {
            Timber.d("movie video status %s", videoResource.status);
            setupTrailer(getContext(), videoResource.data != null ? videoResource.data.getKey() : null);
        });
    }

    private void setupFlexbox() {
        FlexboxLayout flexboxLayout = binding.included.flexbox;
        FlexboxLayout flexBoxGenre = binding.included.flexboxGenre;
        flexboxLayout.removeAllViews();
        flexBoxGenre.removeAllViews();

        int runtime = movieDetails.getRuntime();
        String site = movieDetails.getHomepage();
        int revenue = movieDetails.getRevenue();
        List<Genre> genreList = movieDetails.getGenres();

        if (runtime != 0) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tv_flexbox, flexboxLayout, false);
            int hr = runtime / 60;
            int min = runtime % 60;
            textView.setText(String.format(Locale.ENGLISH, "%d hr %02d min", hr, min));
            textView.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_time), null, null, null);
            flexboxLayout.addView(textView);
        }

        if (!TextUtils.isEmpty(site)) {
            if (getContext() != null) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tv_flexbox, flexboxLayout, false);
                textView.setText(site);
                textView.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_link), null, null, null);
                textView.setAutoLinkMask(Linkify.WEB_URLS);
                textView.setLinkTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                Linkify.addLinks(textView, Linkify.WEB_URLS);
                flexboxLayout.addView(textView);
            }
        }

        if (revenue != 0) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tv_flexbox, flexboxLayout, false);
            textView.setText(NumberFormat.getNumberInstance(Locale.US).format(revenue));
            textView.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_revenue), null, null, null);
            flexboxLayout.addView(textView);
        }

        if (genreList != null && genreList.size() > 0) {
            for (Genre genre : genreList) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tv_genre, flexBoxGenre, false);
                textView.setText(genre.getName());
                flexBoxGenre.addView(textView);
            }
        }
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

    private void setRating(double voteAverage) {
        binding.included.circleProgressBar.setProgressWithAnimation((float) voteAverage);
    }

    private boolean isTitleVisible() {
        Rect scrollBounds = new Rect();
        binding.included.scrollView.getHitRect(scrollBounds);
        return binding.included.txtTitle.getLocalVisibleRect(scrollBounds);
    }

    private Drawable getDrawable(int drawableId) {
        if (getContext() != null) {
            return ContextCompat.getDrawable(getContext(), drawableId);
        }
        return null;
    }

    private void setupTrailer(Context context, String id){
        binding.included.btnTrailer.setOnClickListener(v -> {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }
        });

    }

    private void setupViewPager() {
        DetailsViewPagerAdapter adapter = new DetailsViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ReviewsFragment(), "Reviews");
        adapter.addFragment(new SuggestionsFragment(), "Suggestions");
        binding.included.viewPager.setAdapter(adapter);
        binding.included.tabs.setupWithViewPager(binding.included.viewPager);
        binding.included.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.included.viewPager.scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
