package com.project.pan.kotlin.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pan.kotlin.R
import com.project.pan.kotlin.mvp.contract.IssueBean
import com.project.pan.kotlin.mvp.contract.KFollowContract
import com.project.pan.kotlin.mvp.presenter.KFollowPresenter
import com.project.pan.kotlin.ui.adapter.KFollowAdapter
import kotlinx.android.synthetic.main.khome_fragment.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [KFollowFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [KFollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KFollowFragment : Fragment(),KFollowContract.View {

    private var loadingMore = false
    private var isRefresh = false
    private var mTitle: String? = null
    private lateinit var mPresenter: KFollowPresenter
    private var mKFollowAdapter: KFollowAdapter? = null
    private val layoutManager by lazy {
       LinearLayoutManager(context,RecyclerView.VERTICAL,false)
    }

    companion object {
        private const val ARG_TITLE = "title"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment KFollowFragment.
         */
        fun newInstance(title: String): KFollowFragment {
            val fragment = KFollowFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTitle = arguments!!.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewUI()
    }

    private fun initViewUI() {
        //下拉刷新
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.getFollowInfo()
        }
        mRefreshLayout.setOnLoadMoreListener { it ->
            it.finishLoadMore()
            loadingMore = true
            mPresenter.getMoreFollowInfo()
        }
        mPresenter = KFollowPresenter(context!!,this)
        mPresenter.getFollowInfo()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        mRefreshLayout.finishRefresh()
    }

    /**
     * 获取关注信息
     */
    override fun showFollowInfo(issueBean: IssueBean) {
        mRecyclerView.layoutManager = layoutManager
        context?.let {
            if(mKFollowAdapter == null){
                mKFollowAdapter = KFollowAdapter(R.layout.item_follow,issueBean.itemList)
                mRecyclerView.adapter = mKFollowAdapter
            }else{
                mKFollowAdapter?.setNewData(issueBean.itemList)
                mKFollowAdapter?.notifyDataSetChanged()
            }
        }
    }
    /**
     * 获取更多关注信息
     */
    override fun showMoreFollowInfo(issueBean: IssueBean) {
        context?.let {
            if(mKFollowAdapter == null){
                mKFollowAdapter = KFollowAdapter(R.layout.item_follow,issueBean.itemList)
                mRecyclerView.adapter = mKFollowAdapter
            }else{
                mKFollowAdapter?.addData(issueBean.itemList)
                mKFollowAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun showErrorMsg(errorMsg: String) {
    }

    override fun showNetworkErrorMsg(errorMsg: String) {
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}
