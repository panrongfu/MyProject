package com.project.pan.kotlin.base.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.project.pan.common.datapackage.manager.IRepositoryManager
import com.project.pan.kotlin.base.IModel

/**
 * @author: panrongfu
 * @date: 2018/12/19 16:43
 * @describe:
 */
open class BaseModel(repositoryManager: IRepositoryManager): IModel, LifecycleObserver {

    var mRepositoryManager: IRepositoryManager = repositoryManager

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    override fun onDestroy() {

    }
}