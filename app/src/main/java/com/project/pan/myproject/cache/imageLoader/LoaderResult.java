package com.project.pan.myproject.cache.imageLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author: panrongfu
 * @date: 2018/10/29 11:43
 * @describe:
 */

public class LoaderResult {
    public ImageView imageView;
    public Bitmap bitmap;
    public String url;

    public LoaderResult(ImageView imageView, Bitmap bitmap, String url) {
        this.imageView = imageView;
        this.bitmap = bitmap;
        this.url = url;
    }
}
