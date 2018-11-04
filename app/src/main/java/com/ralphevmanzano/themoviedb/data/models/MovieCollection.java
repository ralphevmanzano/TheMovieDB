package com.ralphevmanzano.themoviedb.data.models;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;

import java.util.List;

public class MovieCollection {
    private List<MinimizedMovie> nowPlayingMovies;
    private List<MinimizedMovie> popularMovies;
    private List<MinimizedMovie> topRatedMovies;
    private List<MinimizedMovie> upcomingMovies;

    public MovieCollection(List<MinimizedMovie> nowPlayingMovies, List<MinimizedMovie> popularMovies, List<MinimizedMovie> topRatedMovies, List<MinimizedMovie> upcomingMovies) {
        this.nowPlayingMovies = nowPlayingMovies;
        this.popularMovies = popularMovies;
        this.topRatedMovies = topRatedMovies;
        this.upcomingMovies = upcomingMovies;
    }

    public List<MinimizedMovie> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public List<MinimizedMovie> getPopularMovies() {
        return popularMovies;
    }

    public List<MinimizedMovie> getTopRatedMovies() {
        return topRatedMovies;
    }

    public List<MinimizedMovie> getUpcomingMovies() {
        return upcomingMovies;
    }

    public int getTotalMovies() {
        return nowPlayingMovies.size() + popularMovies.size() + topRatedMovies.size() + upcomingMovies.size();
    }
}
