package com.ralphevmanzano.themoviedb.databinding;

import android.widget.ImageView;

import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.databinding.BindingAdapter;
import timber.log.Timber;

public final class ImageBindingAdapter {

    @BindingAdapter("url")
    public static void loadImageUrl(ImageView imageView, String url) {
        if (url != null && !url.trim().isEmpty()) {
            Picasso.get().load(Constants.IMAGE_ENDPOINT_PREFIX + url).resize(300, 450).into(imageView);
        }
    }
}
