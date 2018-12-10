package com.ralphevmanzano.themoviedb.data.local.entity;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_movie")
public class Movie {
    public static final String KEY_VOTE_AVERAGE = "Movie.KEY_VOTE_AVERAGE";
    public static final String KEY_VOTE_COUNT = "Movie.KEY_VOTE_COUNT";
    public static final int NOW_PLAYING = 1;
    public static final int POPULAR = 2;
    public static final int TOP_RATED = 3;
    public static final int UPCOMING = 4;
    public static final int LATEST = 5;

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Json(name = "id")
    private long id;
    @Json(name = "vote_count")
    private int voteCount;
    @Json(name = "video")
    private Boolean video;
    @Json(name = "vote_average")
    private double voteAverage;
    @Json(name = "title")
    private String title;
    @Json(name = "popularity")
    private double popularity;
    @Json(name = "poster_path")
    @ColumnInfo(name = "posterPath")
    private String posterPath;
    @Json(name = "original_language")
    private String originalLanguage;
    @Json(name = "original_title")
    private String originalTitle;
//    @Json(name = "genre_ids")
//    private List<Integer> genreIds = null;
    @Json(name = "backdrop_path")
    @ColumnInfo(name = "backdropPath")
    private String backdropPath;
    @Json(name = "adult")
    private Boolean adult;
    @Json(name = "overview")
    private String overview;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "category")
    private String category;
    private Date lastRefresh;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
