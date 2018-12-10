package com.ralphevmanzano.themoviedb.databinding;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ralphevmanzano.themoviedb.data.local.entity.MovieDetails;
import com.ralphevmanzano.themoviedb.data.models.MinimizedMovie;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.databinding.BindingAdapter;

public class DateBindingAdapter {
    @BindingAdapter("setYear")
    public static void setYear(TextView textView, String dateString) {
        if (!TextUtils.isEmpty(dateString)) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                textView.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
