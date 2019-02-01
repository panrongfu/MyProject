package com.project.pan.kotlin.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.project.pan.common.global.ARouterPaths
import com.project.pan.kotlin.R
import com.project.pan.kotlin.R.id.tabLayout
import com.project.pan.kotlin.mvp.model.bean.TabEntity
import com.project.pan.kotlin.ui.fragments.*
import kotlinx.android.synthetic.main.activity_khome.*

@Route(path = ARouterPaths.K_HOME_ACTIVITY)
class KHomeActivity : AppCompatActivity() {

    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)

    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mFragments = ArrayList<Fragment>()
    private var mKHomeFragment: KHomeFragment? = null
    private var mKDiscoveryFragment: KDiscoveryFragment? = null
    private var mKHotFragment: KHotFragment? = null
    private var mKMineFragment: KMineFragment? = null

    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_khome)
        initFragmentTab()
       // switchFragment(mIndex)
    }

    /**
     * 初始tab
     */
    private fun initFragmentTab() {
        mKHomeFragment = KHomeFragment.getInstance("每日精选")
        mKDiscoveryFragment = KDiscoveryFragment.getInstance("发现")
        mKHotFragment = KHotFragment.getInstance("热门")
        mKMineFragment = KMineFragment.getInstance("我的")

        mFragments.add(mKHomeFragment!!)
        mFragments.add(mKDiscoveryFragment!!)
        mFragments.add(mKHotFragment!!)
        mFragments.add(mKMineFragment!!)

        //类似for循环0 - mTitles.size 循环往mTabEntities 添加TabEntity
        (0 until mTitles.size).mapTo(mTabEntities){
            TabEntity(mTitles[it],mIconSelectIds[it],mIconUnSelectIds[it])
        }


        tabLayout.setTabData(mTabEntities,this,R.id.flContainer, mFragments)
        tabLayout.setOnTabSelectListener(object: OnTabSelectListener{
            override fun onTabSelect(position: Int) {
               // switchFragment(position)
                  mIndex = position
                  tabLayout.currentTab = mIndex
            }

            override fun onTabReselect(position: Int) {
            }
        } )
    }

    /**
     * 切换Fragment
     */
//    private fun switchFragment(position: Int) {
//        val transaction = supportFragmentManager.beginTransaction()
//        //hideFragment(transaction)
//        when(position){
//            //首页 判断mKHomeFragment：为null 执行获取KHomeFragment对象，否则执行transaction.show(it)
//            0 -> mKHomeFragment?.let {
//                transaction.show(it)
//            }?: KHomeFragment.getInstance(mTitles[position]).let {
//                mKHomeFragment = it
//                transaction.add(R.id.flContainer,it,"home")
//
//            }
//            //发现
//            1 -> mKDiscoveryFragment?.let {
//                transaction.show(it)
//            }?: KDiscoveryFragment.getInstance(mTitles[position]).let {
//                mKDiscoveryFragment = it
//                transaction.add(R.id.flContainer,it,"discovery")
//            }
//            //热门精选
//            2 -> mKHotFragment?.let {
//                transaction.show(it)
//            }?: BlankFragment().let {
//                mKHotFragment = it
//                transaction.add(R.id.flContainer,it,"hot")
//            }
//            //我的
//            3 -> mKMineFragment?.let {
//                transaction.show(it)
//            }?: KMineFragment.getInstance(mTitles[position]).let {
//                mKMineFragment = it
//                transaction.add(R.id.flContainer,it,"mine")
//            }
//            else ->{
//
//            }
//        }
//        mIndex = position
//        tabLayout.currentTab = mIndex
//        transaction.commitAllowingStateLoss()
//    }

    /**
     * 隐藏fragment
     */
    private fun hideFragment(transaction: FragmentTransaction) {
        //判空隐藏fragment
        mKHomeFragment?.let { transaction.hide(it) }
        mKMineFragment?.let { transaction.hide(it) }
        mKDiscoveryFragment?.let { transaction.hide(it) }
        mKHotFragment?.let { transaction.hide(it) }
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onSaveInstanceState(outState: Bundle) {
//        //super.onSaveInstanceState(outState)
//        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
//        tabLayout?.let {
//            outState.putInt("currTabIndex", mIndex)
//        }
//    }
}
