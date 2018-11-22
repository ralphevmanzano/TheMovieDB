package com.ralphevmanzano.themoviedb.di;

import com.ralphevmanzano.themoviedb.App;
import com.ralphevmanzano.themoviedb.di.modules.AppModule;
import com.ralphevmanzano.themoviedb.di.modules.ActivityBuildersModule;
import com.ralphevmanzano.themoviedb.di.modules.FragmentBuildersModule;
import com.squareup.picasso.Picasso;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class, ActivityBuildersModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App>{}
}
