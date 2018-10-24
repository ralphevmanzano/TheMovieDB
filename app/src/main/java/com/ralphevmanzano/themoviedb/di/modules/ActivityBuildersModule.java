package com.ralphevmanzano.themoviedb.di.modules;

import com.ralphevmanzano.themoviedb.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

	@ContributesAndroidInjector(modules = FragmentBuildersModule.class)
	abstract MainActivity bindMainActivity();
}
