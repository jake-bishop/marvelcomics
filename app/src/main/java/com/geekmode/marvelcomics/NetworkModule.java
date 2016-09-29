package com.geekmode.marvelcomics;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    private Context context;

    NetworkModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    CharacterService getCharacterService() {
        return ServiceGenerator.getService(CharacterService.class, context);
    }
}
