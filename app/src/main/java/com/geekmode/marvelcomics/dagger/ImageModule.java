package com.geekmode.marvelcomics.dagger;

import android.content.Context;

import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.images.PicassoImageUtilImpl;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {

    @Provides
    @Singleton
    public ImageUtil getImageUtil(@Named("application_context") Context context) {
        return new PicassoImageUtilImpl(new Picasso.Builder(context).build(), context);
    }

}
