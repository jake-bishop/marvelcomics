package com.geekmode.marvelcomics;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Jake on 9/27/2016.
 */

public class PicassoImageUtilImpl implements ImageUtil {

    private final Picasso picasso;
    private final Context context;

    PicassoImageUtilImpl(Picasso picasso, Context context){
        this.picasso = picasso;
        this.context = context;
    }

    @Override
    public void loadImage(String imageUrl, ImageView imageView) {
        picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.bishop)
                .error(R.drawable.bishop)
                .into(imageView);
    }

    @Override
    public void loadImage(int resourceId, ImageView imageView) {
        picasso.with(context)
                .load(resourceId)
                .into(imageView);
    }
}
