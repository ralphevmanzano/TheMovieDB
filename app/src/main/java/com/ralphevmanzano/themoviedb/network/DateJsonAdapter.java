package com.ralphevmanzano.themoviedb.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateJsonAdapter {

    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));  IF NEEDED

    @ToJson synchronized String dateToJson(Date d) {
        return dateFormat.format(d);
    }

    @FromJson synchronized Date dateFromJson(String s) throws ParseException {
        return dateFormat.parse(s);
    }
}
