package com.ralphevmanzano.themoviedb.di.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ralphevmanzano.themoviedb.App;
import com.ralphevmanzano.themoviedb.di.AppScope;
import com.ralphevmanzano.themoviedb.data.remote.DateJsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.OkHttp3Downloader;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class NetworkModule {

    @Provides
    @AppScope
    HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @AppScope
    public File file(App app) {
        return new File(app.getApplicationContext().getCacheDir(), "okhttp_cache");
    }

    @Provides
    @AppScope
    Cache cache(File file) {
        return new Cache(file, 50 * 1000 * 1000);
    }

    @Provides
    @AppScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, StethoInterceptor stethoInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(stethoInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @AppScope
    OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }

    @Provides
    @AppScope
    StethoInterceptor stethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @AppScope
    Moshi moshi() {
        return new Moshi.Builder().add(new DateJsonAdapter()).build();
    }
}
