package com.project.pan.kotlin.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pan.common.utils.StatusBarUtil
import com.project.pan.kotlin.R
import com.project.pan.kotlin.mvp.contract.KHomeContract
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import com.project.pan.kotlin.mvp.presenter.KHomePresenter
import com.project.pan.kotlin.ui.activities.SearchActivity
import com.project.pan.kotlin.ui.adapter.KHomeAdapter
import kotlinx.android.synthetic.main.khome_fragment.*
import java.text.SimpleDateFormat
import java.util.*

//别名
typealias ItemBean = KHomeBean.Issue.Item
/**
 * @author: panrongfu
 * @date: 2018/12/19 13:52
 * @describe:
 */
class KHomeFragment: Fragment(),KHomeContract.View {

    private lateinit var presenter: KHomePresenter
    private var mHomeAdapter: KHomeAdapter? = null
    private var mTitle: String? = null
    private val num =1

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }
    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }
    private var loadingMore = false
    private var isRefresh = false
    private lateinit var bannerDataList:ArrayList<ItemBean>

    companion object {
        fun getInstance(title: String): KHomeFragment {
            val fragment = KHomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.khome_fragment,container,false)
    }
    @SuppressLint("NewApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
             initViewUI()
             presenter = context?.let { KHomePresenter(it,this) }!!
             var map = HashMap<String,String>()
             map["num"] = num.toString()
             presenter.getHomeData(map)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initViewUI() {
        //内容跟随偏移
        //mRefreshLayout.setEnableHeaderTranslationContent(true)
        //下拉刷新
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            var map = HashMap<String,String>()
            map["num"] = 1.toString()
            presenter.getHomeData(map)
        }
        mRefreshLayout.setOnLoadMoreListener { it ->
            it.finishLoadMore()
            loadingMore = true
            presenter.getMoreHomeData()
        }

        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if(currentVisibleItemPosition == 0){
                    toolbar.setBackgroundColor(resources.getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                }else{
                    if(mHomeAdapter?.data?.size!! > 1){
                        toolbar.setBackgroundColor(resources.getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.data
                        val item = itemList[currentVisibleItemPosition]
                        if(item.type == "textHeader"){
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        //状态栏透明和间距处理
        activity?.let {
            StatusBarUtil.darkMode(activity)
            StatusBarUtil.setPaddingSmart(activity, toolbar)
        }
//        activity?.let { StatusBarUtil.set(it, toolbar) }
        iv_search.setOnClickListener { openSearchActivity() }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun fristDataOnSuccess(homeBean: KHomeBean) {
        mRefreshLayout.finishRefresh()
        //获取前五个数据
        bannerDataList = homeBean?.issueList[0]?.itemList.take(5).toCollection(ArrayList())
        //丢掉前四个
        var dropAfterList = homeBean?.issueList[0]?.itemList.drop(4)
        mRecyclerView.layoutManager = linearLayoutManager
        if(mHomeAdapter == null){
            context?.let {mHomeAdapter =KHomeAdapter(it, dropAfterList)}
            mHomeAdapter?.setBannerDataList(bannerDataList)
            mRecyclerView.adapter = mHomeAdapter
        }else{
            mHomeAdapter!!.setNewData(dropAfterList)
            mHomeAdapter?.setBannerDataList(bannerDataList)
            mHomeAdapter!!.notifyDataSetChanged()
        }
    }

    override fun fristDataOndataFail(errorMsg: String) {
    }

    override fun moreDataOnSuccess(homeBean: KHomeBean) {
        loadingMore = false
        mHomeAdapter?.addData(homeBean.issueList[0].itemList)
    }

    override fun moreDataOnFail(errorMsg: String) {
    }

    override fun networkError(msg: String) {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName) }
            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}