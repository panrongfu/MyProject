package com.project.pan.kotlin.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pan.common.utils.StatusBarUtil
import com.project.pan.kotlin.R
import com.project.pan.kotlin.mvp.contract.KHotContract
import com.project.pan.kotlin.mvp.model.bean.TabInfoBean
import com.project.pan.kotlin.mvp.presenter.KHotPresenter
import com.project.pan.kotlin.ui.adapter.KFragmentAdapter
import kotlinx.android.synthetic.main.khot_fragment.*

/**
 * @author: panrongfu
 * @date: 2018/12/19 13:53
 * @describe:
 */
class KHotFragment: Fragment(),KHotContract.View {

    private var mTabList = ArrayList<String>()
    private var mFragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    private lateinit var mPresenter: KHotPresenter
    companion object {
        private const val ARG_TITLE = "title"
        fun getInstance(title: String): KHotFragment {
            val fragment = KHotFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TITLE,title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTitle = arguments?.getString(ARG_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.khot_fragment, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewUI()
    }

    private fun initViewUI() {
        tv_header_title.text = mTitle
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }

        context?.let { mPresenter = KHotPresenter(it,this)}
        mPresenter.getTabInfo()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun showTabInfo(tabInfoBean: TabInfoBean) {
        //将给定的变换函数应用于原始数组的每个元素，并将结果附加到给定目标
        //@link https://blog.csdn.net/Sqq_yj/article/details/81508936
        tabInfoBean?.tabInfo.tabList.mapTo(mTabList){it.name}
        tabInfoBean?.tabInfo.tabList.mapTo(mFragments){
            RankFragment.newInstance(it.apiUrl,"")
        }

        mViewPager.adapter = KFragmentAdapter(childFragmentManager,mFragments,mTabList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showErrorMsg(errorMsg: String) {
    }

    override fun showNetworkErrorMsg(errorMsg: String) {
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}