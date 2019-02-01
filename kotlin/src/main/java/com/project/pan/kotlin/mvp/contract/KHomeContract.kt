package com.project.pan.kotlin.mvp.contract

import com.hazz.kotlinmvp.base.IView
import com.project.pan.kotlin.base.IModel
import com.project.pan.kotlin.mvp.model.bean.HomeBean
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import io.reactivex.Observable
import okhttp3.RequestBody

/**
 * @author: panrongfu
 * @date: 2018/12/19 16:12
 * @describe:
 */
 interface KHomeContract {

    interface View: IView{
        fun fristDataOnSuccess(homeBean: KHomeBean)
        fun fristDataOndataFail(errorMsg: String)
        fun moreDataOnSuccess(homeBean: KHomeBean)
        fun moreDataOnFail(errorMsg: String)
        fun networkError(msg: String)
    }

    interface Model: IModel {
        /**
         * 获取首页数据
         */
        fun getHomeData(params: HashMap<String,String>): Observable<KHomeBean>

        /**
         * 获取下一页的数据
         */
        fun getMoreHomeData(url: String): Observable<KHomeBean>
    }
}
