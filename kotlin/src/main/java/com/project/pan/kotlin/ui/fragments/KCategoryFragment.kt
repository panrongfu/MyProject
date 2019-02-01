package com.project.pan.kotlin.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.pan.common.utils.DeviceUtils
import com.project.pan.kotlin.Constants

import com.project.pan.kotlin.R
import com.project.pan.kotlin.R.id.mRecyclerView
import com.project.pan.kotlin.mvp.contract.KCategoryContract
import com.project.pan.kotlin.mvp.model.bean.KCategoryBean
import com.project.pan.kotlin.mvp.presenter.KCategoryPresenter
import com.project.pan.kotlin.ui.activities.CategoryDetailActivity
import com.project.pan.kotlin.ui.adapter.KCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [KCategoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [KCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KCategoryFragment : Fragment(),KCategoryContract.View {

    private var mTitle: String? = null
    private lateinit var mPresenter: KCategoryPresenter
    //允许为null
    private var mKCategoryAdapter: KCategoryAdapter? = null
    private val mLayoutManager by lazy {
             GridLayoutManager(activity,2)
    }
    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(mTitle: String): KCategoryFragment {
            val fragment = KCategoryFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, mTitle)
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
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewUI()
    }

    private fun initViewUI() {
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                //强转基本类型的时候 用to**() 不要用as
                val offset: Int = DeviceUtils.dpToPixel(context,2f).toInt()
                /**
                 * @param left   The X coordinate of the left side of the rectangle
                 * @param top    The Y coordinate of the top of the rectangle
                 * @param right  The X coordinate of the right side of the rectangle
                 * @param bottom The Y coordinate of the bottom of the rectangle
                 */
                outRect.set(
                        if(position.rem(2) == 0) 0 else offset,
                        offset,
                        if(position.rem(2) == 0) 0 else offset,
                        offset
                )
            }
        })
        mPresenter = KCategoryPresenter(context!!,this)
        mPresenter.getCategory()
        mKCategoryAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(context as Activity, CategoryDetailActivity::class.java)
            //因为getItem返回的是object类型 所以需要强转成KCategoryBean
            var data = adapter?.getItem(position) as KCategoryBean
            intent.putExtra(Constants.BUNDLE_CATEGORY_DATA,data)
            context?.startActivity(intent)
         }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showCategory(categoryList: ArrayList<KCategoryBean>) {
        context.let {
            if(mKCategoryAdapter == null){
                mKCategoryAdapter = KCategoryAdapter(context!!,R.layout.item_category,categoryList)
                mRecyclerView.adapter = mKCategoryAdapter
            }else{
                mKCategoryAdapter?.setNewData(categoryList)
                mKCategoryAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun showDataErrorMsg(errorMsg: String) {

    }

    override fun showNetworkErrorMsg(errorMsg: String) {

    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}
