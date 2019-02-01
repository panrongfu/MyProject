
package com.project.pan.common.datapackage.manager;

import android.content.Context;




/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 {@link } 必要的 Api 做数据处理
 * ================================================
 */
public interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cache);



    <T> T obtainTypeService(Class<T> datasource);

    <T> T obtainDBService(Class<T> db);



    /**
     * 清理所有缓存
     */
    void clearAllCache();


    Context getContext();

}
