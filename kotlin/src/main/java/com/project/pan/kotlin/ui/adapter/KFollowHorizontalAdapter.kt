package com.project.pan.kotlin.ui.adapter

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.project.pan.common.glide.GlideApp
import com.project.pan.kotlin.Constants
import com.project.pan.kotlin.R
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import com.project.pan.kotlin.ui.activities.VideoDetailActivity
import com.project.pan.kotlin.utils.durationFormat

typealias IssueItemBean = KHomeBean.Issue.Item
/**
 * @author: panrongfu
 * @date: 2018/12/25 14:30
 * @describe:
 */
class FollowHorizontalAdapter: BaseQuickAdapter<IssueItemBean,BaseViewHolder>  {

    constructor(layoutResId: Int, data: MutableList<IssueItemBean>) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder, item: IssueItemBean) {

        with(helper){
            val horizontalItemData = item?.data
            val path = horizontalItemData?.cover?.feed
            // 加载封页图
            GlideApp.with(mContext)
                    .load(path)
                    .placeholder(R.mipmap.placeholder_banner)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(getView(R.id.iv_cover_feed))

            setText(R.id.tv_title, horizontalItemData?.title ?: "")

            // 格式化时间
            val timeFormat = durationFormat(horizontalItemData?.duration)

            //标签
            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            }else{
                setText(R.id.tv_tag,"#$timeFormat")
            }

            //没用用的参数可以用_代替
            setOnItemClickListener { _, _, _ ->
                goToVideoPlayer(mContext as Activity, getView(R.id.iv_cover_feed), item)
            }
        }
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: IssueItemBean) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        //转场动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}