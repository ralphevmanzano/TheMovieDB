package com.ralphevmanzano.themoviedb.di;

import com.ralphevmanzano.themoviedb.App;
import com.ralphevmanzano.themoviedb.di.modules.AppModule;
import com.ralphevmanzano.themoviedb.di.modules.ActivityBuildersModule;
import com.ralphevmanzano.themoviedb.di.modules.FragmentBuildersModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class, ActivityBuildersModule.class})
public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }
}
