package com.project.pan.kotlin.mvp.presenter

import android.content.Context
import com.hazz.kotlinmvp.base.IView
import com.project.pan.common.datapackage.manager.RepositoryManager
import com.project.pan.kotlin.base.mvp.BasePresenter
import com.project.pan.kotlin.mvp.contract.IssueBean
import com.project.pan.kotlin.mvp.contract.KFollowContract
import com.project.pan.kotlin.mvp.model.KFollowModel
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: panrongfu
 * @date: 2018/12/25 13:24
 * @describe:
 *
 *
 */
class KFollowPresenter(context: Context, rootView: IView): BasePresenter<KFollowContract.View> (context,rootView){
    var repositoryManager = RepositoryManager.newRepositoryManager(context)
    var mModel = KFollowModel(repositoryManager)
    var nextPageUrl:String? = null

    /**
     * 获取关注的信息
     */
    fun getFollowInfo(){
        mModel.getFollowInfo()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { addDispose(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: handleFollowInfoResponse, this:: handleFollowInfoError)
    }

    /**
     * 获取更多关注信息
     * @param url 获取更多数据的请求链接
     * 关于retrofit rxjava kotlin 封装网络请求 可以参考下面的链接,外国友人写的非常详细
     * @link https://medium.com/@gauravpandey_34933/kotlin-with-retrofit-and-rxjava-acff5825454a
     */
    fun getMoreFollowInfo(){
        //判空处理
        nextPageUrl?.let {
            mModel.getMoreFollwInfo(it)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { addDispose(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: handleMoreFollowInfoResponse, this:: handleMoreFollowInfoError)
        }
    }

    /**
     * 获取关注信息成功回调
     */
    private fun handleFollowInfoResponse(issueBean: IssueBean) {
        /**
         * 关于 run with apply 的用法可以查阅博客
         * @Link https://blog.csdn.net/guijiaoba/article/details/54615036
         */
        mRootView.apply {
            hideLoading()
            nextPageUrl = issueBean.nextPageUrl
            showFollowInfo(issueBean)
        }
    }

    /**
     * 获取关注信息错误回调
     */
    private fun handleFollowInfoError(e: Throwable){
        mRootView.apply {
            hideLoading()
            showErrorMsg(e.message?:"")
        }
    }

    /**
     * 获取更多成功回调
     */
    private fun handleMoreFollowInfoResponse(issueBean: IssueBean){
        mRootView.apply {
            hideLoading()
            nextPageUrl = issueBean.nextPageUrl
            showMoreFollowInfo(issueBean)
        }
    }

    /**
     * 获取更多错误回调
     */
    private fun handleMoreFollowInfoError(e: Throwable){
        mRootView.apply {
            hideLoading()
            showErrorMsg(e.message?:"")
        }
    }
}