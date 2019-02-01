package com.project.pan.kotlin

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.project.pan.kotlin.R.id.textView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * @author: panrongfu
 * @date: 2018/12/17 17:06
 * @describe:
 */
class KotinActivity: Activity() {
    val ID_OK = 1
    val ID_CANCEL = 2
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KotlinRecyclerViewUI().setContentView(this)

//
//        val list:MutableList<String> = ArrayList<String>()
//        list.add("1")
//        list.add("2")
//        list.add("3")
//        list.add("4")
//        list.add("5")
//
//        val listView = ListView(this@KotinActivity)
//        setContentView(listView)
//        listView.adapter = ArrayAdapter<String>(
//                this@KotinActivity,
//                android.R.layout.simple_expandable_list_item_1,
//                list)

//
//        relativeLayout {
//            button("ok"){
//                id = ID_OK
//                background = getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha)
//            }.lparams{
//                alignParentTop()
//            }
//            button("cancel").lparams{
//                below(ID_OK)
//                id = ID_CANCEL
//            }
//            /**
//             * 使用 include 函数可以很容易地将 XML 布局插入 Anko Layout DSL 中：
//             */
//            include<View>(R.layout.kotin_activity)
//
//            /**
//             * 叩p lyRecursive ly 函数接受一个 View，里面的内容会作用在这个 View 上，当这是一个
//             * ViewGroup 时效果递归地作用在里面的所有子 View 上
//             */
//        }.applyRecursively {
//
//            when(it.id){
//                ID_OK -> {toast("这是ok 按钮")}
//
//                ID_CANCEL -> {toast("这是取消按钮")}
//            }
//        }
    }

//    class KotinActivityUI: AnkoComponent<KotinActivity>{
//        override fun createView(ui: AnkoContext<KotinActivity>): View = with(ui) {
//        verticalLayout{
//                linearLayout {
//                    view {
//                        backgroundColor = Color.BLUE
//                    }.lparams{
//                        width = matchParent
//                        height = dip(45)
//                    }
//                }.lparams{
//                    width = matchParent
//                    height = dip(200)
//                }
//
//                textView("这是一个TextView"){
//                    textSize = 13f
//                    layoutParams = ViewGroup.LayoutParams(wrapContent, wrapContent)
//                    onClick{
//                        toast("你点我干嘛")
//                    }
//                    backgroundResource = R.drawable.abc_action_bar_item_background_material
//                }.lparams{
//                    gravity = Gravity.CENTER
//                }
//
//                button("ooo"){
//                    textResource = R.string.abc_action_bar_home_description
//
//                }.lparams{
//                    width = dip(75)
//                    height = dip(85)
//                    topMargin = dip(18)
//                    gravity = Gravity.CENTER
//                }
//                imageView{
//                    imageResource = R.drawable.notification_tile_bg
//                }
//
//                editText {
//                    hintResource = R.string.abc_search_hint
//
//                }
//            }
//        }
//    }

    class KotlinRecyclerViewUI: AnkoComponent<KotinActivity>{

        override fun createView(ui: AnkoContext<KotinActivity>): View = with(ui){
            verticalLayout {
                recyclerView{
                    val layoutManager = LinearLayoutManager(context)
                    layoutManager.orientation = OrientationHelper.VERTICAL
                    var dataList = ArrayList<ItemBean>()
                    adapter = RecyclerAdapter(dataList)
                    itemAnimator = DefaultItemAnimator()
                    addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
                    adapter.notifyDataSetChanged()
                }.lparams(matchParent, matchParent)
            }
        }
    }

    class RecyclerAdapter(private val dataList: MutableList<ItemBean>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        internal var mContext: Context? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            mContext = parent.context

            return ViewHolder(ListViewItemUI().createView(AnkoContext.create(parent.context,parent)))
        }

        override fun getItemCount(): Int {
            return dataList!!.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            fun bindView(model: ItemBean){
                holder.titleTv.text = model.title
                Glide.with(mContext!!)
                        .load(model.imgURL?.let {model.imgURL })
                        .into(holder.imageView)

                with(holder.container){
                   setOnClickListener {
                      Toast.makeText(mContext,position,Toast.LENGTH_LONG).show()
                   }
                }
            }
            val item = dataList!![position]
            bindView(item)
        }

        class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            var container = view.find<LinearLayout>(Ids.item_layout)
            var imageView = view.find<ImageView>(Ids.item_image_view)
            var titleTv = view.find<TextView>(Ids.item_text_view_one)
        }
    }

    class ListViewItemUI: AnkoComponent<ViewGroup>{
        override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {
            verticalLayout{
                id = Ids.item_layout
                textView("one"){
                    textColor = Color.DKGRAY
                    textSize = 30F
                    id =Ids.item_text_view_one
                }.lparams(wrapContent, wrapContent)

                imageView{
                    backgroundColor =Color.GRAY
                    id = Ids.item_image_view
                }.lparams(width = dip(40),height = dip(40))
            }
        }.view
    }

    private object Ids {
        const val item_layout = 1
        const val item_image_view = 2
        const val item_text_view_one = 3
    }
}