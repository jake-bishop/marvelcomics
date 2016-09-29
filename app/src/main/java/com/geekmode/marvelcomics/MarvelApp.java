package com.geekmode.marvelcomics;

import android.app.Application;

import com.geekmode.marvelcomics.dagger.ApplicationComponent;
import com.geekmode.marvelcomics.dagger.ApplicationModule;
import com.geekmode.marvelcomics.dagger.DaggerApplicationComponent;
import com.geekmode.marvelcomics.dagger.ImageModule;
import com.geekmode.marvelcomics.dagger.NetworkModule;

public class MarvelApp extends Application {
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .imageModule(new ImageModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
