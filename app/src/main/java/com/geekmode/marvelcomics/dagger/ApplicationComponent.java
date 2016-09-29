package com.geekmode.marvelcomics.dagger;

import com.geekmode.marvelcomics.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jake on 9/27/2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, ImageModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
