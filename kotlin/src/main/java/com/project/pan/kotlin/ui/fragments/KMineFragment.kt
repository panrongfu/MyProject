package com.project.pan.kotlin.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pan.kotlin.R

/**
 * @author: panrongfu
 * @date: 2018/12/19 13:53
 * @describe:
 */
class KMineFragment : Fragment(){

    private var mTitle: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(">>>>>>>>","KMineFragment-onCreate")
    }
    /**
     * 类似java的静态方法
     */
    companion object {
        fun getInstance(title: String): KMineFragment {
            val fragment = KMineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(">>>>>>>>","KMineFragment-onCreateView")
        return inflater.inflate(R.layout.kmine_fragment,container,false)
    }
}