package com.ralphevmanzano.themoviedb.data.models;

import com.squareup.moshi.Json;

public class ProductionCompany {

    @Json(name = "id")
    private int id;
    @Json(name = "logo_path")
    private String logoPath;
    @Json(name = "name")
    private String name;
    @Json(name = "origin_country")
    private String originCountry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
}
