package com.ralphevmanzano.themoviedb.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class NetworkClient {
	
	private static Retrofit retrofit;
	public void NetworkClient(){}
	
	public static Retrofit getRetrofit() {
		if (retrofit == null) {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
					message -> Timber.d(message));
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			OkHttpClient okHttpClient = builder.addInterceptor(httpLoggingInterceptor).build();
			
			retrofit = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/")
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.client(okHttpClient)
					.build();
		}
		
		return retrofit;
	}
	
}
