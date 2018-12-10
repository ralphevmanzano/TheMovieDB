package com.ralphevmanzano.themoviedb.data.models;

import com.squareup.moshi.Json;

public class Genre {

    @Json(name = "id")
    private long id;
    @Json(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
