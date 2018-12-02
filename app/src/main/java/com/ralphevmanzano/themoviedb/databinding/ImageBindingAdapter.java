package com.ralphevmanzano.themoviedb.databinding;

import android.widget.ImageView;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Picasso;

import androidx.core.view.ViewCompat;
import androidx.databinding.BindingAdapter;

public final class ImageBindingAdapter {

    @BindingAdapter("url")
    public static void loadImageUrl(ImageView imageView, String url) {
        if (url != null && !url.trim().isEmpty()) {
            ViewCompat.setTransitionName(imageView, url);
            Picasso.get()
                   .load(Constants.IMAGE_ENDPOINT_PREFIX + url)
                   .resize(300, 450)
                   .placeholder(R.color.shimmerBg)
                   .into(imageView);
        }
    }

    @BindingAdapter("backdrop")
    public static void loadBackdrop(ImageView imageView, String url) {
        if (url != null && !url.trim().isEmpty()) {
            Picasso.get()
                   .load(Constants.BACKDROP_ENDPOINT_PREFIX + url)
                   .resize(1080, 608)
                   .placeholder(R.color.colorPrimary)
                   .into(imageView);
        }
    }
}
