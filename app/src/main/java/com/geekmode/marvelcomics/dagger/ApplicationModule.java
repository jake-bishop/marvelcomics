package com.geekmode.marvelcomics.dagger;

import android.content.Context;

import com.geekmode.marvelcomics.MarvelApp;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.images.PicassoImageUtilImpl;
import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.services.ServiceGenerator;
import com.squareup.picasso.Picasso;

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

    @Provides
    @Singleton
    public ImageUtil getImageUtil() {
        return new PicassoImageUtilImpl(new Picasso.Builder(marvelApp.getApplicationContext()).build(), marvelApp.getApplicationContext());
    }

    @Provides
    @Singleton
    public CharacterService getCharacterService() {
        return ServiceGenerator.getService(CharacterService.class, marvelApp.getApplicationContext());
    }

}
