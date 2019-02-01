package com.project.pan.kotlin.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.project.pan.common.glide.GlideApp
import com.project.pan.kotlin.R
import com.project.pan.kotlin.mvp.model.bean.KCategoryBean
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * @author: panrongfu
 * @date: 2018/12/25 11:14
 * @describe:
 */
class KCategoryAdapter: BaseQuickAdapter<KCategoryBean, BaseViewHolder> {

    private var textTypeface: Typeface?=null

    constructor(context: Context,resId: Int,data: MutableList<KCategoryBean>):super(resId,data){
        textTypeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }


    override fun convert(holder: BaseViewHolder, item: KCategoryBean?) {
        with(holder){
            setText(R.id.tv_category_name,"#${item?.name}")
            getView<TextView>(R.id.tv_category_name).typeface = textTypeface
            getView<ImageView>(R.id.ivCategory).run {
                GlideApp.with(mContext)
                        .load(item?.bgPicture)
                        .placeholder(R.color.color_darker_gray)
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(ivCategory)
            }
        }
    }
}