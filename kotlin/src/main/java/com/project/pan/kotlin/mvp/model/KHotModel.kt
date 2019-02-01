package com.project.pan.kotlin.mvp.model

import com.hazz.kotlinmvp.api.ApiService
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.kotlin.base.mvp.BaseModel
import com.project.pan.kotlin.mvp.contract.KHotContract
import com.project.pan.kotlin.mvp.model.bean.TabInfoBean
import io.reactivex.Observable

/**
 * @author: panrongfu
 * @date: 2018/12/25 17:00
 * @describe:
 */
class KHotModel(repositoryManager: IRepositoryManager): BaseModel(repositoryManager),KHotContract.Model {

    override fun getTabInfo(): Observable<TabInfoBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getRankList()
    }
}