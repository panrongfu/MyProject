package com.project.pan.myproject.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.project.pan.myproject.R;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/7/10 20:36
 * @describe: 自定义指示器布局
 */

public class IndicatorLayout extends LinearLayout {

    private int DEFAULT_POINT_SIZE = 4;
    private int DEFAULT_SELECTED_WIDTH = 10;
    private int DEFAULT_UNSELECTED_COLOR = Color.WHITE;
    private int DEFAULT_SELECTED_COLOR = Color.RED;

    /**带动画的小圆点*/
    private PointView pointView;
    /**item的个数*/
    private int itemSize;
    /**当前item的位置*/
    private int currentItemPosition=0;
    /**上一个圆点的位置*/
    private int lastItemPosition=0;

    private int pointSize;
    private int pointSelectedWidth;
    private int unSelectedColor;
    private int selectedColor;

    private List<PointView>  pointList = new ArrayList();

    public IndicatorLayout(Context context) {
        super(context);
        initView(null);
    }

    public IndicatorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public IndicatorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    /**
     * 初始化视图
     * @param attrs
     */
    private void initView(AttributeSet attrs){
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.indicatorLayout);

        pointSize = (int) ta.getDimension(R.styleable.indicatorLayout_pointSize,DEFAULT_POINT_SIZE);
        pointSelectedWidth = (int) ta.getDimension(R.styleable.indicatorLayout_pointSelectedWidth,DEFAULT_SELECTED_WIDTH);
        unSelectedColor = ta.getColor(R.styleable.indicatorLayout_pointUnSelectedColor,DEFAULT_UNSELECTED_COLOR);
        selectedColor = ta.getColor(R.styleable.indicatorLayout_pointSelectedColor,DEFAULT_SELECTED_COLOR);

        ta.recycle();
    }

    /**
     * 获取圆点个数
     * @return
     */
    public int getItemSize() {
        return itemSize;
    }

    /**
     * 设置圆点个数
     * @param itemSize
     */
    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
        if(itemSize == 0){
            // TODO: 2018/7/11 如果只有一个item则不现实指示器布局
            return;
        }
        /*动态添加itemSize个圆点*/
        for(int i = 0; i < itemSize; i++){
            /*创建圆点对象*/
            PointView pointView = new PointView(getContext());
            pointView.setpColor(unSelectedColor);//未选中圆点的颜色
            pointView.setpStretchColor(selectedColor);//选中圆点的颜色
            /*设置属性*/
            LayoutParams params = new LayoutParams(pointSize, pointSize);
            params.leftMargin = pointSize;
            pointView.setLayoutParams(params);
            /*添加到LinearLayout*/
            addView(pointView);
            /*同时添加到list中用来控制圆点的操作逻辑*/
            pointList.add(pointView);
        }
    }

    /**
     * 获取当前显示的圆点位置
     * @return
     */
    public int getCurrentItemPosition() {
        return currentItemPosition;
    }

    /**
     * 设置当前圆点所在位置
     * @param currentPosition
     */
    public void setCurrentItemPosition(int currentPosition) {
        this.currentItemPosition = currentPosition;
        PointView currentPoint = pointList.get(currentPosition);
        LayoutParams params = new LayoutParams(pointSelectedWidth, pointSize);
        params.leftMargin = pointSize;
        currentPoint.setLayoutParams(params);
        currentPoint.setLayoutStartStretch(true);
        currentPoint.setLayoutChange(true);

        /**
         * 如果当前的圆点跟上一个圆点位置相同 上个圆点跟当前圆点是同一个
         * 如果当前的圆点位置跟上一个圆点位置不相同，则需要获取上个圆点，还原动画
         */
        if(lastItemPosition != currentPosition){
            PointView lastPoint = pointList.get(lastItemPosition);
            LayoutParams paramsLastPoint = new LayoutParams(pointSize, pointSize);
            paramsLastPoint.leftMargin = pointSize;
            lastPoint.setLayoutParams(paramsLastPoint);
            lastPoint.setLayoutStartStretch(false);
        }
        //把当前圆点位置保存起来与下个圆点的位置对比
        lastItemPosition = currentPosition;
        
    }

    public  int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pix转dip
     */
    public int pix2dip(float pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }
}
