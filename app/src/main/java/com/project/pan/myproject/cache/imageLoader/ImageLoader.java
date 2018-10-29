package com.project.pan.myproject.cache.imageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.project.pan.myproject.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: panrongfu
 * @date: 2018/10/27 9:56
 * @describe:
 */

public class ImageLoader {
    public static final String TAG = "ImageLoader";
    private boolean mIsDiskLruCacheCreated = false;
    private int DISK_CACHE_SIZE = 1024*1024*50;
    private int MESSAGE_POST_RESULT = 1;
    private int IO_BUFFER_SIZE = 8*1024;
    private int DISK_CAHCE_INDEX = 0;
    private int TAG_KEY_URL = R.id.cache_id;
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDisLruCache;
    private ImageResizer mImageResizer;
    private Context mContext;

    @SuppressLint("HandlerLeak")
    private Handler mMainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          //  super.handleMessage(msg);
            LoaderResult result = (LoaderResult)msg.obj;
            ImageView imageView = result.imageView;

            imageView.setImageBitmap(result.bitmap);
            String url  = (String) imageView.getTag(TAG_KEY_URL);
            if(!TextUtils.isEmpty(url) && url.equals(result.url)){
                imageView.setImageBitmap(result.bitmap);
            }else {
                Log.w(TAG,"set image bitmap ,but url has change");
            }
        }
    };


    public ImageLoader(Context context) {
        this.mContext = context;
        mImageResizer = new ImageResizer();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //内存缓存大小，是系统分配给应用的内存大小的1/8
        int cacheMemroySize = maxMemory/8;

        mMemoryCache = new LruCache<String,Bitmap>(cacheMemroySize){

            /**
             * 作用只要是定义缓存中每项的大小
             * @param key
             * @param bitmap
             * @return
             */
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

        File diskCacheDir = getDiskCacheDir(context,"bitmap");
        if(!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        //创建磁盘缓存时候，需要判断磁盘剩余的空间，是否能够满足我们磁盘缓存的大小50MB
        if(getUsableSpace(diskCacheDir)> DISK_CACHE_SIZE){
            try {
                //创建diskLruCache
                mDisLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    private long getUsableSpace(File diskCacheDir) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return diskCacheDir.getUsableSpace();
        }
        final StatFs statFs = new StatFs(diskCacheDir.getPath());
        return statFs.getBlockSizeLong()*statFs.getAvailableBlocksLong();
    }

    /**
     * 获取磁盘缓存路径
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if(externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath+File.separator+uniqueName);
    }

    /**
     * 创建imageloader 实例
     * @param context
     * @return
     */
    public static ImageLoader build(Context context){
        return new ImageLoader(context);
    }

    /**
     * 把bitmap添加到缓存
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key,bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    /**
     * 获取网络图片
     * 磁盘缓存的添加需要通过Editor来完成
     * @param url
     * @return
     */
    public Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException {
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can't not visit network in UI thread");
        }
        if(mDisLruCache == null){
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDisLruCache.edit(key);
        if (editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CAHCE_INDEX);
            if(downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else {
                editor.abort();
            }
            mDisLruCache.flush();
        }
        return loadBitmapFromDiskCache(url,reqWidth,reqHeight);
    }

    /**
     * 从磁盘获取缓存bitmap
     * 磁盘缓存的读取需要通过Snapshot来完成，通过Snapshot（ /'snæpʃɒt/）
     * 获取到缓存对象FileInputSteam，但是fileInputStream无法便捷的进行压缩，
     * 所以通过FileDescriptor来加载压缩后的图片，最后将加载后的bitmap添加早内存缓存中
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {
        if(Looper.myLooper() == Looper.getMainLooper()){
            Log.w(TAG,"load bitmap from UI thread,it's not recommend");
        }
        if(mDisLruCache == null){
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mDisLruCache.get(key);
        if(snapshot != null){
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CAHCE_INDEX);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor,reqWidth,reqHeight);
            if(bitmap != null){
                addBitmapToMemoryCache(key,bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 从内存缓存中 获取bitmap
     * @param url
     * @return
     */
    private Bitmap loadBitmapFromMemoryCache(String url){
        final String key = hashKeyFromUrl(url);
        Bitmap bitmap = getBitmapFromMemoryCache(key);
        return bitmap;
    }

    /**
     * 获取bitmap 从网络或是内存缓存、磁盘缓存
     * @param url http url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap loadBitmap(String url, int reqWidth, int reqHeight){
        //先从内存中获取
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if(bitmap != null){
            Log.d(TAG,"loadBitmapFromMemoryCache, URL:"+url);
            return bitmap;
        }

        try {
            bitmap = loadBitmapFromDiskCache(url,reqWidth,reqHeight);
            if(bitmap != null){
                Log.d(TAG,"loadBitmapFromDiskCache, URL:"+url);
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(url,reqWidth,reqHeight);
            Log.d(TAG,"loadBitmapFromHttp, URL:"+url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null && !mIsDiskLruCacheCreated){
            Log.w(TAG,"diskLruCache is not create");
            bitmap = downloadBitmapFromUrl(url);
        }
        return bitmap;
    }

    /**
     * 异步获取bitmap然后设置imageview
     * @param url
     * @param imageView
     * @param reqWidth
     * @param reqHeight
     */
    public void bindBitmap(String url, ImageView imageView,int reqWidth, int reqHeight){
        imageView.setTag(TAG_KEY_URL,url);
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url,reqWidth,reqHeight);
                if(bitmap != null){
                    LoaderResult result = new LoaderResult(imageView,bitmap,url);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,result).sendToTarget();
                }
            }
        };
       ExecutorService executorService =  Executors.newFixedThreadPool(5);
       executorService.execute(loadBitmapTask);

    }

    /**
     *
     * @param url
     * @param imageView
     */
    public void bindBitmap(String url, ImageView imageView){
        bindBitmap(url,imageView,0,0);
    }

    /**
     * 下载文件流
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);
            int b;
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 从网络获取bitmap
     * @param urlString
     * @return
     */
    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection= null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e(TAG,"error in downloadBitmapFromUrl:"+e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     * byte 转成String
     * @param bytes
     * @return
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<bytes.length; i++){
            String hex = Integer.toHexString(0XFF&bytes[i]);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
