package com.geekmode.marvelcomics.injection;

import com.geekmode.marvelcomics.view.CharacterFragment;
import com.geekmode.marvelcomics.view.ComicListActivity;
import com.geekmode.marvelcomics.view.ComicListViewHolder;
import com.geekmode.marvelcomics.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(CharacterFragment fragment);
    void inject(ComicListActivity comicListActivity);
    void inject(ComicListViewHolder comicListViewHolder);
}
