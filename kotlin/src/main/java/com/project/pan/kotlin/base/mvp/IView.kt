package com.hazz.kotlinmvp.base

/**
 * @author Jake.Ho
 * created: 2017/10/25
 * desc:
 */
interface IView {

    /**
     * 显示加载,下拉刷新
     */
    fun showLoading()
    /**
     * 隐藏加载,刷新完获取
     */
    fun hideLoading()

}
