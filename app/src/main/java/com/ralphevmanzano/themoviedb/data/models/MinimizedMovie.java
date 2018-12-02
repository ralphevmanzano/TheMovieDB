package com.ralphevmanzano.themoviedb.data.models;

import com.squareup.moshi.Json;

import java.util.List;

public class MinimizedMovie {

    @Json(name = "id")
    private long id;
    @Json(name = "title")
    private String title;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "category")
    private String category;

    public MinimizedMovie(long id, String title, String posterPath, String backdropPath, String category) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }
}
