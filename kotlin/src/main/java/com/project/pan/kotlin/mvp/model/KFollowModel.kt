package com.project.pan.kotlin.mvp.model

import com.hazz.kotlinmvp.api.ApiService
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.kotlin.base.mvp.BaseModel
import com.project.pan.kotlin.mvp.contract.IssueBean
import com.project.pan.kotlin.mvp.contract.KFollowContract
import io.reactivex.Observable

/**
 * @author: panrongfu
 * @date: 2018/12/25 13:21
 * @describe:
 */
class KFollowModel(repositoryManager: IRepositoryManager): BaseModel(repositoryManager),KFollowContract.Model {

    override fun getFollowInfo(): Observable<IssueBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getFollowInfo()
    }

    override fun getMoreFollwInfo(url: String): Observable<IssueBean> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getMoreIssueData(url)
    }
}