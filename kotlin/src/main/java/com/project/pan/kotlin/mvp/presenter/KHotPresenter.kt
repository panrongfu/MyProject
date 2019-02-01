package com.project.pan.kotlin.mvp.presenter

import android.content.Context
import com.hazz.kotlinmvp.base.IView
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.common.datapackage.manager.RepositoryManager
import com.project.pan.kotlin.base.mvp.BasePresenter
import com.project.pan.kotlin.mvp.contract.KHotContract
import com.project.pan.kotlin.mvp.model.KHotModel
import com.project.pan.kotlin.mvp.model.bean.TabInfoBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: panrongfu
 * @date: 2018/12/25 17:03
 * @describe:
 */
class KHotPresenter(context: Context,rootView: IView): BasePresenter<KHotContract.View>(context,rootView) {

    var repositoryManager: IRepositoryManager = RepositoryManager.newRepositoryManager(mContext)
    var mModel = KHotModel(repositoryManager)

    /**
     * 获取热门类目
     */
    fun getTabInfo(){
        mModel.getTabInfo()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { addDispose(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleTabInfoResponse,this::handleTabInfoError)

    }

    private fun handleTabInfoResponse(tabInfoBean: TabInfoBean){
        mRootView.apply {
            hideLoading()
            showTabInfo(tabInfoBean)
        }
    }

    private fun handleTabInfoError(e: Throwable){
        mRootView.apply {
            hideLoading()
            showErrorMsg(e?.message?:"")
        }
    }
}