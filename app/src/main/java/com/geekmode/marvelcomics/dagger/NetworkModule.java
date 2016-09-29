package com.geekmode.marvelcomics.dagger;

import android.content.Context;

import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.services.ServiceGenerator;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    CharacterService getCharacterService(@Named("application_context") Context context) {
        return ServiceGenerator.getService(CharacterService.class, context);
    }

}
