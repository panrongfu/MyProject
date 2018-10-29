package com.project.pan.myproject.cache.imageLoader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * @author: panrongfu
 * @date: 2018/10/29 9:42
 * @describe: 图片压缩功能
 */

public class ImageResizer {

    public static final String TAG = "ImageResizer";

    public ImageResizer() {

    }

    public Bitmap decodeSampledBitmapFromeResource(Resources res, int resId, int reqWidth, int reqHeight){
        //1).inJustDecodeBounds = true 不会加载到内存中
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeResource(res,resId,options);
        //2).计算 insampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //3).设置insample后，再获取bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth, int reqHeight){
        //1).inJustDecodeBounds = true 不会加载到内存中
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        //2).计算 insampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //3).设置insampleSize再获取bitmap
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }

    /**
     * 计算insampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if(reqWidth == 0 || reqHeight == 0){
            return 1;
        }

        //获取原始bitmap的宽高
        final  int height = options.outHeight;
        final  int width = options.outWidth;
        Log.d(TAG,"orgin width:"+width+"orgin height:"+height);

        int inSampleSize = 1;
        if(height > reqHeight || width> reqWidth){
            final  int halfHeight = height/2;
            final  int halfWidth = width/2;

            //计算最大inSampleSize的值
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth ){
                inSampleSize *=2;
            }
        }
        return  inSampleSize;
    }
}
