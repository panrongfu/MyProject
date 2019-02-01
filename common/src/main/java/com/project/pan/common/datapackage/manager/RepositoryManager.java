
package com.project.pan.common.datapackage.manager;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.project.pan.common.global.Global;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link } 层必要的 Api 做数据处理
 * ================================================
 */

public class RepositoryManager implements IRepositoryManager {

    private static final int TIME_OUT = 10;

   Retrofit mRetrofit;

//    RxCache mRxCache;
//    @Inject
//    Lazy<ResourceHelper> mResourceHelper;

    Application mApplication;

//    Cache.Factory mCachefactory;//根据cachetype创建缓存
//
//    private Cache<String, Object> mRetrofitServiceCache;//网络请求数据管理
//    private Cache<String, Object> mCacheServiceCache; //统一三级缓存管理 rxcache
//    private Cache<String, Object> mResourceServiceCache;//统一管理资源 数据库，sp，json
//    private Cache<String, Object> mDBServiceCache;

    private Context mContext;
    private String token = "";

    static RepositoryManager repositoryManager;

    public static RepositoryManager newRepositoryManager(Context cxt){
        synchronized (RepositoryManager.class){
            if (repositoryManager == null){
                return new RepositoryManager(cxt.getApplicationContext());
            }else {
                return repositoryManager;
            }
        }
    }

    private RepositoryManager(Context cxt) {
        mContext = cxt;
        mRetrofit = getRetrofit();

    }

    /**
     * 获取Retrofit
     * @return
     */
    private Retrofit getRetrofit(){

        return new Retrofit.Builder()
                .baseUrl(Global.BASE_URL)  //自己配置
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取okhttpClient
     * @return
     */
    private OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 请求的缓存的大小跟位置
        File cacheFile = new File(mContext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小
        return new OkHttpClient.Builder()
                .addInterceptor(addHeaderInterceptor())
                .addInterceptor(addQueryParameterInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)  //添加缓存
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 设置头
     */
    private Interceptor addHeaderInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();
                requestBuilder.header("token", token);
                requestBuilder.method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }
    /**
     * 设置公共参数
     */
    private Interceptor addQueryParameterInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl.Builder modifiedUrl = originalRequest.url().newBuilder();
                modifiedUrl.addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5");
                modifiedUrl.addQueryParameter("deviceModel","abc");
                Request request = originalRequest.newBuilder().url(modifiedUrl.build()).build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     * 缓存retrofit接口
     * @param service
     * @param <T>
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public synchronized <T> T obtainRetrofitService(Class<T> service) {
//        if (mRetrofitServiceCache == null) //若内存缓存为空 放入
//            mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE);
//        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
//        T retrofitService = (T) mRetrofitServiceCache.get(service.getCanonicalName());//从内存缓存中拿出
//        if (retrofitService == null) {
//            retrofitService = mRetrofit.get().create(service);
//            mRetrofitServiceCache.put(service.getCanonicalName(), retrofitService);
//        }
        return mRetrofit.create(service);
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     * @param
     * @param
     * @return
     */
    @Override
    public synchronized <T> T obtainCacheService(Class<T> cache) {//通过rxcache
//        if (mCacheServiceCache == null)
//            mCacheServiceCache = mCachefactory.build(CacheType.CACHE_SERVICE_CACHE);
//        Preconditions.checkNotNull(mCacheServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
//        T cacheService = (T) mCacheServiceCache.get(cache.getCanonicalName());
//        if (cacheService == null) {
//            cacheService = mRxCache.get().using(cache);
//            mCacheServiceCache.put(cache.getCanonicalName(), cacheService);
//        }
        return null;
    }


    @Override
    public <T> T obtainTypeService(Class<T> datasource) {
//        if(datasource.getCanonicalName().equals("com.mujirenben.android.common.datapackage.resource.ResourceHelper")){
//            if(mResourceServiceCache==null){
//                mResourceServiceCache = mCachefactory.build(CacheType.RESOURCE_SERVICE_CACHE);
//            }
//            Preconditions.checkNotNull(mResourceServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
//            T resource = (T) mResourceServiceCache.get(datasource.getCanonicalName());//从内存缓存中拿出
//            if (resource == null) {
//                resource = (T) new ResourceHelper(getContext());
//                mResourceServiceCache.put(datasource.getCanonicalName(),resource);
//                Logger.d("----获取resource资源（不是缓存resourceHelper）----");
//            }else {
//                Logger.d("----获取resource资源（缓存resourceHelper）----");
//            }
//            return resource;
//        }
        return null;
    }

    @Override
    public <T> T obtainDBService(Class<T> db) {
//            if(mDBServiceCache==null){
//                mDBServiceCache = mCachefactory.build(CacheType.DB_SERVICE_CACHE);
//            }
//            Preconditions.checkNotNull(mDBServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
//            T resource = (T) mDBServiceCache.get(db.getCanonicalName());//从内存缓存中拿出
//            if (resource == null) {
//                resource = (T) ArmsUtils.obtainAppComponentFromContext(BaseApplication.getApplication()).getDBHelper();
//                mDBServiceCache.put(db.getCanonicalName(),resource);
//                Logger.d("----获取DB资源（不是缓存DBHelper）----");
//            }else {
//                Logger.d("----获取DB资源（缓存DBHelper）----");
//            }
            return null;
    }


    protected Disposable changeIOToMainThread(Observable<ResponseBody> observable , DisposableObserver<ResponseBody> consumer ){
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {
      //  mRxCache.get().evictAll();
    }


    @Override
    public Context getContext() {
        return mApplication;
    }
}
