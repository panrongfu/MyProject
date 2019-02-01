package com.project.pan.kotlin.mvp.presenter

import android.content.Context
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.common.datapackage.manager.RepositoryManager
import com.project.pan.kotlin.base.mvp.BasePresenter
import com.project.pan.kotlin.mvp.contract.KCategoryContract
import com.project.pan.kotlin.mvp.model.KCategoryModel
import com.project.pan.kotlin.mvp.model.bean.KCategoryBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: panrongfu
 * @date: 2018/12/25 10:20
 * @describe:
 */
class KCategoryPresenter(context: Context, rootView: KCategoryContract.View): BasePresenter<KCategoryContract.View>(context,rootView) {

    var repositoryManager: IRepositoryManager = RepositoryManager.newRepositoryManager(mContext)
    var mModel = KCategoryModel(repositoryManager)

    /**
     * 获取分类
     */
    fun getCategory(){
        mModel.getCategory()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { addDispose(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: handleResponse,this::handleError)
    }

    private fun handleResponse(categoryList: ArrayList<KCategoryBean>){
        mRootView.showCategory(categoryList)
    }

    private fun handleError(e: Throwable){

    }
}