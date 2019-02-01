package com.project.pan.kotlin.base

/**
 * @author: panrongfu
 * @date: 2018/12/19 16:35
 * @describe:
 */
interface IModel {
    /**
     * 在框架中 [BasePresenter.onDestroy] 时会默认调用 [IModel.onDestroy]
     */
    fun onDestroy()
}