package com.ralphevmanzano.themoviedb.data.models;

import com.ralphevmanzano.themoviedb.data.local.entity.Video;
import com.squareup.moshi.Json;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class VideosResponse {
    @Json(name = "id")
    private long id;
    @Json(name = "results")
    private List<Video> videos;

    public VideosResponse(long id, List<Video> videos) {
        this.id = id;
        this.videos = videos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> results) {
        this.videos = results;
    }
}