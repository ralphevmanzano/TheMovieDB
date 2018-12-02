package com.ralphevmanzano.themoviedb.data.local.entity;

import com.squareup.moshi.Json;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_movie_details_video")
public class Video {
    @PrimaryKey
    private long movieId;
    @Json(name = "id")
    private String id;
    @Json(name = "key")
    private String key;
    @Json(name = "name")
    private String name;
    @Json(name = "site")
    private String site;
    @Json(name = "size")
    private int size;
    @Json(name = "type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}
