package com.geekmode.marvelcomics;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {

    private Context context;

    ImageModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Picasso getPicasso() {
        return new Picasso.Builder(context).build();
    }

    @Provides
    @Singleton
    ImageUtil getImageUtil() {
        return new PicassoImageUtilImpl(new Picasso.Builder(context).build(), context);
    }

}
