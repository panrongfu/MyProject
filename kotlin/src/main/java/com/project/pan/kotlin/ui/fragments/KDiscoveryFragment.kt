package com.project.pan.kotlin.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pan.common.utils.StatusBarUtil
import com.project.pan.kotlin.R
import com.project.pan.kotlin.ui.adapter.KFragmentAdapter
import com.project.pan.kotlin.utils.TabLayoutHelper
import kotlinx.android.synthetic.main.kdiscovery_fragment.*

/**
 * @author: panrongfu
 * @date: 2018/12/19 13:53
 * @describe:
 */
class KDiscoveryFragment: Fragment() {

    private var tabList = ArrayList<String>()
    private var fragments = ArrayList<Fragment>()


    private var mTitle: String? = null

    companion object {
        private const val ARG_TITLE = "title"
        fun getInstance(title: String): KDiscoveryFragment {
            val fragment = KDiscoveryFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TITLE,title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTitle = arguments!!.getString(ARG_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.kdiscovery_fragment,null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewUI()
    }

    private fun initViewUI() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }

        //设置title
        tvHeaderTitle.text = mTitle

        //初始化tab
        tabList.add("关注")
        tabList.add("分类")

        //初始化fragment
        fragments.add(KFollowFragment.newInstance("关注"))
        fragments.add(KCategoryFragment.newInstance("分类"))

        //getSupportFragmentManager() 替换为getChildFragmentManager()
        mViewPager.adapter = KFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }
}