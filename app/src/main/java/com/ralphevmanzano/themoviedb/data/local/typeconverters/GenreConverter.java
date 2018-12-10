package com.ralphevmanzano.themoviedb.data.local.typeconverters;

import com.ralphevmanzano.themoviedb.data.models.Genre;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class GenreConverter {

    private final JsonAdapter<List<Genre>> adapter;

    public GenreConverter() {
        Moshi moshi = new Moshi.Builder().build();
        Type listGenre = Types.newParameterizedType(List.class, Genre.class);
        adapter = moshi.adapter(listGenre);
    }

    @TypeConverter
    public String listToString(List<Genre> genreList) {
        return adapter.toJson(genreList);
    }

    @TypeConverter
    public List<Genre> stringToList(String jsonList) {
        try {
            return adapter.fromJson(jsonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
