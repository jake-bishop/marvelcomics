package com.geekmode.marvelcomics.images;

import android.widget.ImageView;

public interface ImageUtil {
    void loadImage(String imageUrl, ImageView imageView);
    void loadImage(int resourceId, ImageView imageView);
}
