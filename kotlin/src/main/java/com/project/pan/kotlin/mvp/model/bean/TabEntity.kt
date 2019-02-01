package com.project.pan.kotlin.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @author: panrongfu
 * @date: 2018/12/19 14:34
 * @describe:
 */
class TabEntity(var title: String, var selectIcon: Int,var unSelectedIcon: Int): CustomTabEntity {

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}