package com.project.pan.common.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.LruCache;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author: panrongfu
 * @date: 2018/12/21 16:35
 * @describe:
 */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
      //  builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024));
    }
}
