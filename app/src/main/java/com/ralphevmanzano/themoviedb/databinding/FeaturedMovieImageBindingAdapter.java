package com.ralphevmanzano.themoviedb.databinding;

import android.widget.ImageView;

import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class FeaturedMovieImageBindingAdapter {
    @BindingAdapter("latesturl")
    public static void loadImageUrl(ImageView imageView, Object data) {
        if (data != null) {
            if (data instanceof MinimizedMovie) {
                String url = ((MinimizedMovie) data).getBackdropPath();
                if ( url != null && !url.trim().isEmpty()) {
                    Picasso.get().load(Constants.FEATURED_IMAGE_ENDPOINT_PREFIX + url).resize(1280, 720).into(imageView);
                }
            }
        }
    }
}
