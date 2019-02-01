package com.project.pan.kotlin.mvp.contract

import com.hazz.kotlinmvp.base.IView
import com.project.pan.kotlin.base.IModel
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import io.reactivex.Observable

typealias IssueBean = KHomeBean.Issue

/**
 * @author: panrongfu
 * @date: 2018/12/25 13:07
 * @describe:
 */
interface KFollowContract {

    interface View: IView{
        fun showFollowInfo(issueBean: IssueBean)
        fun showErrorMsg(errorMsg: String)
        fun showNetworkErrorMsg(errorMsg: String)

        fun showMoreFollowInfo(issueBean: IssueBean)
    }

    interface Model: IModel{
        /**
         * 获取关注的信息
         */
        fun getFollowInfo(): Observable<IssueBean>

        /**
         * 获取更多关注信息
         * @param url 获取更多数据的请求链接
         */
        fun getMoreFollwInfo(url: String): Observable<IssueBean>
    }
}