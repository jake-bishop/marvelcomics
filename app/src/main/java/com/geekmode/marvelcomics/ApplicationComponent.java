package com.geekmode.marvelcomics;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

/**
 * Created by Jake on 9/27/2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, ImageModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
