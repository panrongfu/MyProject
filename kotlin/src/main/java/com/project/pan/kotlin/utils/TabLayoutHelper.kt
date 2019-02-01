package com.project.pan.kotlin.utils

import android.hardware.display.DisplayManager
import android.os.Build
import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import com.project.pan.common.utils.DeviceUtils
import java.lang.reflect.Field

/**
 * @author: panrongfu
 * @date: 2018/12/24 17:49
 * @describe: 单例模式的实现只需要一个 object 关键字即可
 */
object TabLayoutHelper {

     fun setUpIndicatorWidth(mTabLayout: TabLayout){

         var tableLayoutClass = mTabLayout.javaClass
         var mTabStrip:Field

        //获取私有属性mTabStrip
         mTabStrip = tableLayoutClass.getDeclaredField("mTabStrip")
         mTabStrip.isAccessible = true

        //通过获取属性值，强转为LinearLayout
         var layout: LinearLayout = mTabStrip.get(mTabLayout) as LinearLayout

        //循环获取到layout里面的子view
         for (i in 0 until layout.childCount){
             var childView = layout.getChildAt(i)

           var params = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1f)
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                 params.marginStart = DeviceUtils.dpToPixel(childView.context,50f).toInt()
                 params.marginEnd = DeviceUtils.dpToPixel(childView.context,50f).toInt()
             }
             childView.layoutParams = params
             //更改布局参数了，重绘
             childView.invalidate()
         }
    }
}