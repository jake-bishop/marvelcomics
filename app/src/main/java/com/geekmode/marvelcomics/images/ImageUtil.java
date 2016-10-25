package com.geekmode.marvelcomics.images;

import android.content.Context;
import android.widget.ImageView;

public interface ImageUtil {
    void loadImage(String imageUrl, ImageView imageView);
    void loadImage(int resourceId, ImageView imageView);
    void loadImage(Context withContext, String imageUrl, ImageView imageView);
}
