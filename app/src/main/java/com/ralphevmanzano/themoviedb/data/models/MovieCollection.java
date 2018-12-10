package com.ralphevmanzano.themoviedb.data.models;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;

import java.util.List;

public class MovieCollection {
    private List<Movie> nowPlayingMovies;
    private List<Movie> popularMovies;
    private List<Movie> topRatedMovies;
    private List<Movie> upcomingMovies;

    public MovieCollection(List<Movie> nowPlayingMovies, List<Movie> popularMovies, List<Movie> topRatedMovies, List<Movie> upcomingMovies) {
        this.nowPlayingMovies = nowPlayingMovies;
        this.popularMovies = popularMovies;
        this.topRatedMovies = topRatedMovies;
        this.upcomingMovies = upcomingMovies;
    }

    public List<Movie> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public List<Movie> getPopularMovies() {
        return popularMovies;
    }

    public List<Movie> getTopRatedMovies() {
        return topRatedMovies;
    }

    public List<Movie> getUpcomingMovies() {
        return upcomingMovies;
    }

    public int getTotalMovies() {
        return nowPlayingMovies.size() + popularMovies.size() + topRatedMovies.size() + upcomingMovies.size();
    }
}
