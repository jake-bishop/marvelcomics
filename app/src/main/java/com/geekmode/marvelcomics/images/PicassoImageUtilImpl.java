package com.geekmode.marvelcomics.images;

import android.content.Context;
import android.widget.ImageView;

import com.geekmode.marvelcomics.R;
import com.squareup.picasso.Picasso;

public class PicassoImageUtilImpl implements ImageUtil {

    private final Picasso picasso;
    private final Context context;

    public PicassoImageUtilImpl(Picasso picasso, Context context){
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
