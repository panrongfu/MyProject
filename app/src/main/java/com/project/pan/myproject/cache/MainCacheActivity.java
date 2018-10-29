package com.project.pan.myproject.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.project.pan.myproject.R;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainCacheActivity extends AppCompatActivity {

    private DiskLruCache mDiskLruCache;
    private long DISK_CACHE_MAX_SIZE = 50;
    private GridView gridView;

    private String url = "http://pic29.nipic.com/20130511/9252150_174018365301_2.jpg";
    private List<String> list =new ArrayList<>();
    private SquareImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        gridView = findViewById(R.id.gridView);
        for(int i=0;i<100;i++){
            list.add(url);
        }
        adapter = new SquareImageAdapter(list,this);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    adapter.setGridViewIdle(true);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.setGridViewIdle(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void makeDiskLruCache() {
        try {
            File cacheDir = getDiskCacheDir(this, "bitmapCache");

            if (!cacheDir.exists()) {
               // Log.d(TAG, "缓存目录不存在，创建之...");
                cacheDir.mkdirs();
            } else
              //,  Log.d(TAG, "缓存目录已存在，不需创建.");

            //第二个参数我选取APP的版本code。DiskLruCache如果发现第二个参数version不同则销毁缓存
            //第三个参数为1，在写缓存的流时候，newOutputStream(0)，0为索引，类似数组的下标
            mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_MAX_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //把byte字节写入缓存DiskLruCache
    private void writeToDiskLruCache(String url, byte[] buf) throws Exception {

        //DiskLruCache缓存需要一个key，我先把url转换成md5字符串，
        //然后以md5字符串作为key键
        String key="";
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);

        OutputStream os = editor.newOutputStream(0);
        os.write(buf);
        os.flush();
        editor.commit();

        mDiskLruCache.flush();

       // Log.d(TAG, url + " : 写入缓存完成.");
    }

    /**
    * 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，
    * 否则就调用getCacheDir()方法来获取缓存路径。
    * 前者获取到的就是 /sdcard/Android/data/<application package>/cache
    * 而后者获取到的是 /data/data/<application package>/cache 。
    **/
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        File dir = new File(cachePath + File.separator + uniqueName);
        return dir;
    }
}
