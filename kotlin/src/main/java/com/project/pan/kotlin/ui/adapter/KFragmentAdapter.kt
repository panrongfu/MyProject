package com.project.pan.kotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author: panrongfu
 * @date: 2018/12/24 17:06
 * @describe:
 */
class KFragmentAdapter: FragmentPagerAdapter {

    private var mFragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, titles: List<String>):super(fm){
        this.mFragmentList = fragmentList
        this.mTitles = titles
        mFragmentList?.run {
            val transaction = fm.beginTransaction()
            fragmentList.forEach {
                transaction.remove(it)
            }
            transaction.commitAllowingStateLoss()
            fm.executePendingTransactions()
            notifyDataSetChanged()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //if else 判空
        return if(null != mTitles) mTitles!![position] else ""
    }
    override fun getItem(position: Int): Fragment {
        return mFragmentList!![position]
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }
}