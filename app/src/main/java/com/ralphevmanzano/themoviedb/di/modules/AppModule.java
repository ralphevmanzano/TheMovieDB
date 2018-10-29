package com.ralphevmanzano.themoviedb.di.modules;

import com.ralphevmanzano.themoviedb.App;
import com.ralphevmanzano.themoviedb.data.MovieDao;
import com.ralphevmanzano.themoviedb.data.MovieDatabase;
import com.ralphevmanzano.themoviedb.di.AppScope;
import com.ralphevmanzano.themoviedb.network.MovieDBService;
import com.ralphevmanzano.themoviedb.utils.Constants;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module(includes = {AndroidInjectionModule.class, NetworkModule.class, ViewModelModule.class})
public class AppModule {

    @Provides
    @AppScope
    MovieDBService provideApiService(OkHttpClient okHttpClient, Moshi moshi) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(MovieDBService.class);
    }

    @Provides
    @AppScope
    Picasso picasso(App app, OkHttp3Downloader okHttp3Downloader) {
        Picasso picasso = new Picasso.Builder(app.getApplicationContext())
                .downloader(okHttp3Downloader)
                .indicatorsEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }

    @Provides
    @AppScope
    MovieDatabase provideMovieDatabase(App app) {
        return Room.databaseBuilder(app.getApplicationContext(), MovieDatabase.class, "movies.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @AppScope
    MovieDao provideMovieDao(MovieDatabase movieDatabase) {
        return movieDatabase.movieDao();
    }

}
