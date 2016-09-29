package com.geekmode.marvelcomics;

import android.widget.ImageView;

import java.net.URI;

/**
 * Created by Jake on 9/27/2016.
 */

public interface ImageUtil {
    void loadImage(String imageUrl, ImageView imageView);
    void loadImage(int resourceId, ImageView imageView);
}
