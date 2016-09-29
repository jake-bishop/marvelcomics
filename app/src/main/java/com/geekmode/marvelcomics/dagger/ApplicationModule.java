package com.geekmode.marvelcomics.dagger;

import android.content.Context;

import com.geekmode.marvelcomics.MarvelApp;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private MarvelApp marvelApp;

    public ApplicationModule(MarvelApp marvelApp){
        this.marvelApp = marvelApp;
    }

    @Provides
    @Singleton
    public MarvelApp getApplication(){
        return marvelApp;
    }

    @Provides
    @Singleton
    @Named("application_context")
    public Context getApplicationContext(){
        return marvelApp.getApplicationContext();
    }
}
