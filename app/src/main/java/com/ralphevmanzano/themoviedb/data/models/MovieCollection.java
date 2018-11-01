package com.ralphevmanzano.themoviedb.data.models;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;

import java.util.List;

public class MovieCollection {
    private List<Movie> nowPlayingMovies;
    private List<Movie> popularMovies;

    public MovieCollection(List<Movie> nowPlayingMovies, List<Movie> popularMovies) {
        this.nowPlayingMovies = nowPlayingMovies;
        this.popularMovies = popularMovies;
    }

    public List<Movie> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public List<Movie> getPopularMovies() {
        return popularMovies;
    }

    public int getTotalMovies() {
        return nowPlayingMovies.size() + popularMovies.size();
    }
}
