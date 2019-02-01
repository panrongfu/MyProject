package com.project.pan.kotlin.mvp.model

import com.hazz.kotlinmvp.api.ApiService
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.kotlin.base.mvp.BaseModel
import com.project.pan.kotlin.mvp.contract.KCategoryContract
import com.project.pan.kotlin.mvp.model.bean.KCategoryBean
import io.reactivex.Observable

/**
 * @author: panrongfu
 * @date: 2018/12/25 10:38
 * @describe:
 */
class KCategoryModel(repositoryManager: IRepositoryManager):BaseModel(repositoryManager),KCategoryContract.Model {

    /**
     * 获取分类
     */
    override fun getCategory(): Observable<ArrayList<KCategoryBean>> {
        return  mRepositoryManager.obtainRetrofitService(ApiService::class.java).getCategory()
    }
}