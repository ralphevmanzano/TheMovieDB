package com.ralphevmanzano.themoviedb.databinding;

import android.widget.ImageView;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Picasso;

import androidx.core.view.ViewCompat;
import androidx.databinding.BindingAdapter;

public final class ImageBindingAdapter {

    @BindingAdapter("url")
    public static void loadImageUrl(ImageView imageView, String url) {
        if (url != null && !url.trim().isEmpty()) {
            Picasso.get()
                   .load(Constants.IMAGE_ENDPOINT_PREFIX + url)
                   .noFade()
                   .resize(300, 450)
                   .placeholder(R.color.shimmerBg)
                   .into(imageView);
        }
    }

    @BindingAdapter("latesturl")
    public static void loadImageUrl(ImageView imageView, Object data) {
        if (data != null) {
            if (data instanceof Movie) {
                String url = ((Movie) data).getBackdropPath();
                if ( url != null && !url.trim().isEmpty()) {
                    Picasso.get().load(Constants.FEATURED_IMAGE_ENDPOINT_PREFIX + url).resize(1280, 720).into(imageView);
                }
            }
        }
    }
}
