package com.ralphevmanzano.themoviedb.data.local.typeconverters;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {
	@TypeConverter
	public static Date toDate(Long timestamp) {
		return timestamp == null ? null : new Date(timestamp);
	}
	
	@TypeConverter
	public static Long toTimeStamp(Date date) {
		return date == null ? null : date.getTime();
	}
}
