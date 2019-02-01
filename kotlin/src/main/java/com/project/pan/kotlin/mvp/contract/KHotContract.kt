package com.project.pan.kotlin.mvp.contract

import com.hazz.kotlinmvp.base.IView
import com.project.pan.kotlin.base.IModel
import com.project.pan.kotlin.mvp.model.bean.TabInfoBean
import io.reactivex.Observable

/**
 * @author: panrongfu
 * @date: 2018/12/25 16:52
 * @describe:
 */
interface KHotContract {

    interface View: IView{
        fun showTabInfo(tabInfoBean: TabInfoBean)
        fun showErrorMsg(errorMsg: String)
        fun showNetworkErrorMsg(errorMsg: String)
    }

    interface Model: IModel{
        /**
         * 获取热门类目
         */
        fun getTabInfo(): Observable<TabInfoBean>
    }
}