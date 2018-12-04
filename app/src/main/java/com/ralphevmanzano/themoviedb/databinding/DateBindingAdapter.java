package com.ralphevmanzano.themoviedb.databinding;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class DateBindingAdapter {
    @BindingAdapter("getYear")
    public static void getYear(TextView textView, String date) {
        if (!TextUtils.isEmpty(date.trim())) {
            textView.setText(date);
        }
    }
}
