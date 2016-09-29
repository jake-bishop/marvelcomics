package com.geekmode.marvelcomics;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ImageModule.class})
public interface ImageComponent {

    Picasso getPicasso();

}
