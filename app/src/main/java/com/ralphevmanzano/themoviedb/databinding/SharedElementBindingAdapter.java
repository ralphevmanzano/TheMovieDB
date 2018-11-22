package com.ralphevmanzano.themoviedb.databinding;

import android.widget.ImageView;

import com.ralphevmanzano.themoviedb.ui.main.MovieListFragment;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;

public class SharedElementBindingAdapter {

    private static MovieListFragment fragment;

    @Inject
    public SharedElementBindingAdapter(MovieListFragment fragment) {
        SharedElementBindingAdapter.fragment = fragment;
    }

    @BindingAdapter("sharedElement")
    public static void bindSharedImage(ImageView imageView, String url) {
        Picasso.get().load(Constants.IMAGE_ENDPOINT_PREFIX + url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                fragment.startPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                fragment.startPostponedEnterTransition();
            }
        });
    }
}
