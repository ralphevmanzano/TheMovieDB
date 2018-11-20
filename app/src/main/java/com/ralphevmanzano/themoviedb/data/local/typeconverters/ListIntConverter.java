package com.ralphevmanzano.themoviedb.data.local.typeconverters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class ListIntConverter {

    private final JsonAdapter<List<Integer>> adapter;

    public ListIntConverter() {
        Moshi moshi = new Moshi.Builder().build();
        Type listInteger = Types.newParameterizedType(List.class, Integer.class);
        adapter = moshi.adapter(listInteger);
    }

    @TypeConverter
    public String listToString(List<Integer> integerList) {
        return adapter.toJson(integerList);
    }

    @TypeConverter
    public List<Integer> stringToList(String jsonList) {
        try {
            return adapter.fromJson(jsonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
