package com.project.pan.kotlin.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.project.pan.common.glide.GlideApp
import com.project.pan.kotlin.R
import com.project.pan.kotlin.R.id.banner
import com.project.pan.kotlin.mvp.model.bean.KHomeBean
import com.project.pan.kotlin.utils.durationFormat
import io.reactivex.Observable

//别名
typealias ItemBean = KHomeBean.Issue.Item

/**
 * @author: panrongfu
 * @date: 2018/12/20 17:25
 * @describe:
 */
class KHomeAdapter(context: Context, dataList: List<ItemBean>)
    : BaseMultiItemQuickAdapter<ItemBean, BaseViewHolder>(dataList) {

    companion object {
        private const val ITEM_TYPE_CONTENT = 0
        private const val ITEM_TYPE_BANNER = 2
        private const val ITEM_TYPE_TEXT_HEADER = 1
    }

    private var mBannerDataList:ArrayList<ItemBean> = ArrayList()
    init{
        addItemType(ITEM_TYPE_CONTENT, R.layout.item_home_content)
        addItemType(ITEM_TYPE_BANNER, R.layout.item_home_banner)
        addItemType(ITEM_TYPE_TEXT_HEADER, R.layout.item_home_header)

    }
    /**
     * 设置banner的数据集
     */
    fun setBannerDataList(bannerDataList:ArrayList<ItemBean>){
        mBannerDataList.clear()
        mBannerDataList = bannerDataList
    }

    override fun convert(holder: BaseViewHolder?, item: ItemBean?) {
        when(holder!!.itemViewType){
            ITEM_TYPE_CONTENT -> bindContent(holder,item)
            ITEM_TYPE_BANNER -> bindBanner(holder,item)
            ITEM_TYPE_TEXT_HEADER -> bindHeader(holder,item)

        }
    }

    /**
     * 重写viewTpye，根据位置返回不同类型的视图
     */
    override fun getItemViewType(position: Int): Int {
       return when {
            position == 0 -> ITEM_TYPE_BANNER
            mData[position].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    /**
     * 普通内容
     */
    private fun bindContent(holder: BaseViewHolder, item: ItemBean?) {
        val itemData = item?.data

        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }

        with(holder){
            // 加载封页图
            GlideApp.with(mContext)
                    .load(cover)
                    .placeholder(R.mipmap.placeholder_banner)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(getView(R.id.iv_cover_feed))

            // 如果提供者信息为空，就显示默认
            if (avatar.isNullOrEmpty()) {
                GlideApp.with(mContext)
                        .load(defAvatar)
                        .placeholder(R.mipmap.default_avatar).circleCrop()
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_avatar))

            } else {
                GlideApp.with(mContext)
                        .load(avatar)
                        .placeholder(R.mipmap.default_avatar).circleCrop()
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_avatar))
            }

            setText(R.id.tv_title, itemData?.title ?: "")

            //遍历标签
            itemData?.tags?.take(4)?.forEach { tagItem ->
                tagText += (tagItem.name + "/")
            }

            // 格式化时间
            val timeFormat = durationFormat(itemData?.duration)
            tagText += timeFormat
            holder.setText(R.id.tv_tag, tagText!!)

            holder.setText(R.id.tv_category, "#" + itemData?.category)

            setOnItemClickListener { adapter, view, position ->
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item!!)
            }
        }
    }

    /**
     * 绑定头部（单独个item，并不是整个listview的header）
     */
    private fun bindHeader(holder: BaseViewHolder, item: ItemBean?) {
        with(holder){
            getView<TextView>(R.id.tvHeader).run {
                text = item?.data?.text ?: ""
            }
        }
    }

    /**
     * 绑定轮播图
     */
    private fun bindBanner(holder: BaseViewHolder, item: ItemBean?) {
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()

        //取出banner 显示的 img 和 Title
        Observable.fromIterable(mBannerDataList)
                .subscribe { itemBean->
                    bannerFeedList.add(itemBean.data.cover.feed)
                    bannerTitleList.add(itemBean.data.title)
                }

        //它是将某对象作为函数的参数，在函数块内可以通过 this 指代该对象。
        // 返回值为函数块的最后一行或指定return表达式

        //run函数实际上可以说是let和with两个函数的结合体，
        // run函数只接收一个lambda函数为参数，以闭包形式返回，返回值为最后一行的值或者指定的return的表达式。

        //没有使用到的参数在 kotlin 中用"_"代替
        with(holder){
            getView<BGABanner>(R.id.banner).run {
                setAutoPlayAble(bannerFeedList.size > 1)
                setData(bannerFeedList,bannerTitleList)
                setAdapter{banner,_,feedImageUrl,position ->
                    GlideApp.with(mContext)
                            .load(feedImageUrl)
                            .transition(DrawableTransitionOptions().crossFade())
                            .placeholder(R.mipmap.placeholder_banner)
                            .into(banner.getItemImageView(position))
                }
            }
        }
        with(holder){
            getView<BGABanner>(R.id.banner).setDelegate { banner, itemView, model, position ->
                goToVideoPlayer(mContext as Activity, itemView, mBannerDataList[position])
            }
        }
    }

    /**
     * 播放视频
     */
    private fun goToVideoPlayer(activity: Activity, itemView: View?, item: ItemBean) {

    }
}