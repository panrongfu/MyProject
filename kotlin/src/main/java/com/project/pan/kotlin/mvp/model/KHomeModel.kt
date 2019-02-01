package com.project.pan.kotlin.mvp.model

import com.hazz.kotlinmvp.api.ApiService
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.kotlin.base.mvp.BaseModel
import com.project.pan.kotlin.mvp.contract.KHomeContract
import com.project.pan.kotlin.mvp.model.bean.HomeBean
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import io.reactivex.Observable

/**
 * @author: panrongfu
 * @date: 2018/12/19 16:43
 * @describe:
 */
class KHomeModel(repositoryManager: IRepositoryManager): BaseModel(repositoryManager),KHomeContract.Model {

    /**
     * 获取首页数据
     */
    override fun getHomeData(params: HashMap<String,String>): Observable<KHomeBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getFirstHomeData(params)
    }

    /**
     * 获取下一页数据
     */
    override fun getMoreHomeData(url: String): Observable<KHomeBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getMoreHomeData(url)
    }
}