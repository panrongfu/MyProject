package com.project.pan.kotlin.mvp.presenter

import android.content.Context
import android.util.Log
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.common.datapackage.manager.RepositoryManager
import com.project.pan.kotlin.base.mvp.BasePresenter
import com.project.pan.kotlin.mvp.contract.KHomeContract
import com.project.pan.kotlin.mvp.model.KHomeModel
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: panrongfu
 * @date: 2018/12/20 10:02
 * @describe:
 */
class KHomePresenter(context: Context,kHomeView:KHomeContract.View): BasePresenter<KHomeContract.View>(context,kHomeView) {

     var repositoryManager: IRepositoryManager = RepositoryManager.newRepositoryManager(mContext)
     var mModel: KHomeContract.Model = KHomeModel(repositoryManager)
     private var bannerHomeBean: KHomeBean? = null
     //加载首页的Banner 数据+一页数据合并后，nextPageUrl没 add
     private lateinit var nextPageUrl:String


    /**
     * presenter层，条用model层执行获取数据的方法
     */
    fun getHomeData(parameters: HashMap<String,String>){
        mRootView.showLoading()
        mModel.getHomeData(parameters)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { addDispose(it) }
                .flatMap({homeBean->
                    val bannerItemList = homeBean.issueList[0].itemList
                    bannerItemList?.filter { item ->
                        item.type == "banner2"|| item.type == "horizontalScrollCard"
                    }?.forEach { item ->
                        bannerItemList.remove(item)
                    }
                    //记录第一页是当做 banner 数据
                    bannerHomeBean = homeBean
                    //根据 nextPageUrl 请求下一页数据
                    var nextPageUrl = homeBean.nextPageUrl
                    nextPageUrl?.let { mModel.getMoreHomeData(it)}
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::firstHandleResponse,this::firstHandleError)
    }

    /**
     * 获取下一页数据
     */
     fun getMoreHomeData(){
        mRootView.showLoading()
        mModel.getMoreHomeData(nextPageUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { addDispose(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this:: moreHandleResponse, this:: moreHandleError)
    }

    /**
     * 首次请求成功数据处理
     */
    private fun firstHandleResponse(homeBean: KHomeBean) {
        nextPageUrl = homeBean.nextPageUrl
        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
        val newBannerItemList = homeBean.issueList?.get(0)?.itemList
        newBannerItemList?.filter { item ->
            item.type == "banner2"|| item.type == "horizontalScrollCard"
        }?.forEach { item ->
            newBannerItemList.remove(item)
        }

        // 重新赋值 Banner 长度
        bannerHomeBean!!.issueList?.get(0)?.count = bannerHomeBean!!.issueList[0].itemList.size
        newBannerItemList?.let { bannerHomeBean?.issueList?.get(0)?.itemList?.addAll(it)}
        mRootView.fristDataOnSuccess(bannerHomeBean!!)
    }

    /**
     * 首页请求异常数据处理
     */
    private fun firstHandleError(error: Throwable) {
        Log.d(">>>>>>>>handleError", error.message+"")
    }

    /**
     * 下一页请求成功数据处理
     */
    private fun moreHandleResponse(homeBean: KHomeBean) {
        var newItemList = homeBean?.issueList?.get(0)?.itemList

        newItemList?.filter { item ->
            item.type=="banner2"||item.type=="horizontalScrollCard"
        }?.forEach { item ->
            //移除banner类型的item
            newItemList.remove(item)
        }
        nextPageUrl = homeBean.nextPageUrl
        //回调view层的数据请求成功方法
        mRootView.moreDataOnSuccess(homeBean)
    }

    /**
     * 下一页请求异常数据处理
     */
    private fun moreHandleError(error: Throwable) {
        Log.d(">>>>>>>>handleError", error.localizedMessage)
    }
}