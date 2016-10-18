package com.geekmode.marvelcomics.injection;

import android.content.Context;

import com.geekmode.marvelcomics.services.CharacterService;
import com.geekmode.marvelcomics.services.ServiceGenerator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    public CharacterService provideCharacterService(@Named("application_context") Context context) {
        return ServiceGenerator.getService(CharacterService.class, context);
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

}
