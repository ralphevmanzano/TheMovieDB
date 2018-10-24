package com.ralphevmanzano.themoviedb.data.models;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_movie")
public class Movie {
    @PrimaryKey
    @Json(name = "id")
    private Integer id;
    @Json(name = "vote_count")
    private Integer voteCount;
    @Json(name = "video")
    private Boolean video;
    @Json(name = "vote_average")
    private Double voteAverage;
    @Json(name = "title")
    private String title;
    @Json(name = "popularity")
    private Double popularity;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "original_language")
    private String originalLanguage;
    @Json(name = "original_title")
    private String originalTitle;
    @Json(name = "genre_ids")
    @Ignore
    private List<Integer> genreIds = null;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "adult")
    private Boolean adult;
    @Json(name = "overview")
    private String overview;
    @Json(name = "release_date")
    private String releaseDate;

    private Date lastRefresh;

    /**
     * No args constructor for use in serialization
     */
    public Movie() {
    }

    public Movie(Integer voteCount, Integer id, Boolean video, Double voteAverage, String title,
                 Double popularity, String posterPath, String originalLanguage, String originalTitle,
                 List<Integer> genreIds, String backdropPath, Boolean adult, String overview,
                 String releaseDate, Date lastRefresh) {
        super();
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.lastRefresh = lastRefresh;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
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
}
