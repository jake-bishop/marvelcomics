package com.geekmode.marvelcomics.injection;

import com.geekmode.marvelcomics.CharacterFragment;
import com.geekmode.marvelcomics.ComicListActivity;
import com.geekmode.marvelcomics.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(CharacterFragment fragment);
    void inject(ComicListActivity comicListActivity);
}
