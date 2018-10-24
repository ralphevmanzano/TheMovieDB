package com.ralphevmanzano.themoviedb.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ralphevmanzano.themoviedb.R;
import com.ralphevmanzano.themoviedb.data.models.Movie;
import com.ralphevmanzano.themoviedb.ui.main.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class MoviesAdapter extends BaseAdapter<MoviesAdapter.MoviesHolder, Movie> {
	
	private List<Movie> movieList = new ArrayList<>();
	private Picasso picasso;

	@Inject
	public MoviesAdapter(Picasso picasso) {
		this.picasso = picasso;
	}

	@Override
	public void setData(List<Movie> movies) {
		movieList = movies;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movies,parent,false);
		return new MoviesHolder(v);
	}
	
	@Override
	public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
		holder.tvTitle.setText(movieList.get(position).getTitle());
		holder.tvOverview.setText(movieList.get(position).getOverview());
		holder.tvReleaseDate.setText(movieList.get(position).getReleaseDate());
		
		picasso.load("https://image.tmdb.org/t/p/w500" + movieList.get(position).getPosterPath()).into(holder.ivMovie);
//		Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieList.get(position).getPosterPath()).into(holder.ivMovie);
	}
	
	@Override
	public int getItemCount() {
		return movieList.size();
	}
	
	class MoviesHolder extends RecyclerView.ViewHolder {
		TextView tvTitle,tvOverview,tvReleaseDate;
		ImageView ivMovie;
		
		MoviesHolder(View v) {
			super(v);
			tvTitle = v.findViewById(R.id.tvTitle);
			tvOverview = v.findViewById(R.id.tvOverView);
			tvReleaseDate = v.findViewById(R.id.tvReleaseDate);
			ivMovie = v.findViewById(R.id.ivMovie);
		}
	}
}
