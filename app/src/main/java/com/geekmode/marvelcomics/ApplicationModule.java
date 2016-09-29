package com.geekmode.marvelcomics;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private MarvelApp marvelApp;

    public ApplicationModule(MarvelApp marvelApp){
        this.marvelApp = marvelApp;
    }

    @Provides
    public MarvelApp getApplication(){
        return marvelApp;
    }

    @Provides
    @Named("application_context")
    public Context getApplicationContext(){
        return marvelApp.getApplicationContext();
    }
}
