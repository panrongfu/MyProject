package com.hazz.kotlinmvp.base

import android.app.Activity


/**
 * @author Jake.Ho
 * created: 2017/10/25
 * desc: Presenter 基类
 */


interface IPresenter {

    /**
     * 做一些初始化操作
     */
     fun onStart()

    /**
     * 在框架中 [Activity.onDestroy] 时会默认调用 [IPresenter.onDestroy]
     */
     fun onDestroy()

}
