package com.geekmode.marvelcomics.injection;

import android.content.Context;

import com.geekmode.marvelcomics.ComicListPresenter;
import com.geekmode.marvelcomics.MainPresenter;
import com.geekmode.marvelcomics.context.BaseApplication;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.images.PicassoImageUtilImpl;
import com.geekmode.marvelcomics.services.CharacterService;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private BaseApplication application;

    public ApplicationModule(BaseApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    public BaseApplication provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    @Named("application_context")
    public Context provideApplicationContext(){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public ImageUtil provideImageUtil(@Named("application_context") Context context) {
        return new PicassoImageUtilImpl(new Picasso.Builder(context).build(), context);
    }

    @Provides
    public MainPresenter provideMainPresenter(CharacterService characterService, SchedulerProvider schedulerProvider){
        return new MainPresenter(characterService, schedulerProvider);
    }

    @Provides
    public ComicListPresenter provideComicListPresenter(CharacterService characterService, SchedulerProvider schedulerProvider){
        return new ComicListPresenter(characterService, schedulerProvider);
    }
}
