package com.project.pan.kotlin.ui.adapter

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.project.pan.common.glide.GlideApp
import com.project.pan.kotlin.R
import com.project.pan.kotlin.mvp.contract.IssueBean
import com.project.pan.kotlin.mvp.model.bean.HomeBean
import com.project.pan.kotlin.mvp.model.bean.KHomeBean

typealias IssueItmeBean = KHomeBean.Issue.Item

/**
 * @author: panrongfu
 * @date: 2018/12/25 14:12
 * @describe:
 */
class KFollowAdapter: BaseQuickAdapter<IssueItmeBean,BaseViewHolder> {

    constructor(layoutResId: Int, data: MutableList<IssueItmeBean>) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder, item: IssueItmeBean) {
        bindView(helper,item)
    }

    private fun bindView(helper: BaseViewHolder, item: IssueItmeBean) {

        with(helper){
            var headerData = item.data.header
            var path = headerData.icon
            GlideApp.with(mContext)
                    .load(path)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(getView(R.id.iv_avatar))

            setText(R.id.tv_title, headerData.title)
            setText(R.id.tv_desc, headerData.description)

            //设置嵌套水平的 RecyclerView
            val recyclerView = getView<RecyclerView>(R.id.fl_recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false)
            recyclerView.adapter = FollowHorizontalAdapter(R.layout.item_follow_horizontal,item.data.itemList)
        }
    }
}