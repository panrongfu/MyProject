package com.project.pan.kotlin.mvp.contract

import com.hazz.kotlinmvp.base.IView
import com.project.pan.kotlin.base.IModel
import com.project.pan.kotlin.mvp.model.bean.KCategoryBean
import io.reactivex.Observable

/**
 * @author: panrongfu
 * @date: 2018/12/25 10:21
 * @describe:
 */
interface KCategoryContract {

    interface View: IView{

        fun showCategory(categoryList: ArrayList<KCategoryBean>)

        fun showDataErrorMsg(errorMsg: String)

        fun showNetworkErrorMsg(errorMsg: String)
    }

    interface Model: IModel{
        //获取种类
        fun getCategory(): Observable<ArrayList<KCategoryBean>>
    }
}