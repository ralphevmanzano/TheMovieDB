package com.ralphevmanzano.themoviedb.data.models;

import com.squareup.moshi.Json;

public class MinimizedMovie {

    @Json(name = "id")
    private int id;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "backdrop_path")
    private String backdropPath;

    public MinimizedMovie(int id, String posterPath, String backdropPath) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
