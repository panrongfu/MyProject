package com.project.pan.myproject.view.toothbrushChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author: panrongfu
 * @CreateDate: 2019/5/5 14:14
 * @Description: java类作用描述
 */
public class ToothBrushRecordsChart extends View {
    /**用来保存图标绘制的矩形*/
    private Rect chartRect = new Rect();

    /**虚线画笔*/
    private Paint dashLinePaint;
    private Path dashLinePath;

    private Paint linePaint;
    private Paint labelPaint;
    /**柱状图画笔*/
    private Paint columnarPaint;

    private static String[] leftLabels;
    private static String[] bottomLabels;

    private static int perNum = 8;
    private int LINE_HEIGHT = 4;

    public ToothBrushRecordsChart(Context context) {
        super(context);
    }

    public ToothBrushRecordsChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToothBrushRecordsChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 初始化图表
     */
    public void initChart() {
        initLabels();
        //标签文字画笔
        labelPaint = new Paint();
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(32f);
        labelPaint.setAntiAlias(true);

        //柱状图画笔
        columnarPaint = new Paint();
        columnarPaint.setColor(Color.RED);
        columnarPaint.setTextSize(32f);
        columnarPaint.setStyle(Paint.Style.FILL);
        columnarPaint.setAntiAlias(true);

        //虚横线画笔
        dashLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dashLinePaint.setStrokeWidth(LINE_HEIGHT);
        dashLinePaint.setStyle(Paint.Style.STROKE);//画线条，线条有宽度
        dashLinePaint.setColor(Color.GRAY);
        dashLinePaint.setPathEffect(new DashPathEffect(new float[] {20, 5}, 0));
        dashLinePath = new Path();

        //实横线画笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(LINE_HEIGHT);
        linePaint.setStyle(Paint.Style.STROKE);//画线条，线条有宽度
        linePaint.setColor(Color.GREEN);
        dashLinePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
       // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        getDrawingRect(chartRect);
        drawLines(canvas);
        drawLeftLabels(canvas);
        drawBottomLabels(canvas);
        drawColumnar(canvas);
    }


    /**
     * 绘制柱状图
     * @param canvas
     */
    private void drawColumnar(Canvas canvas) {
        //控件的宽度
        int width = chartRect.right - chartRect.left;
        //根据标签的个数平均分x轴，每一份的宽度
        float partWidth = (float)width / (perNum - 1);
        float startX = chartRect.left + partWidth;
       // canvas.drawLine(startX,chartRect.bottom, startX , 90,columnarPaint);
        RectF r2=new RectF();                           //RectF对象
        r2.left=startX - 26;                                 //左边
        r2.top=400;                                 //上边
        r2.right=startX + 40;                                   //右边
        r2.bottom=chartRect.bottom - getTextHeight(labelPaint) - LINE_HEIGHT;
        canvas.drawRoundRect(r2,10,10,columnarPaint);
    }

    /**
     * 画横线
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        //因为底部需要显示标签所以需要偏移 底部文字高度
        float height = chartRect.bottom - chartRect.top - getTextHeight(labelPaint);
        //计算每个标签占的高度
        float part = height / (leftLabels.length );
        for (int i = 0; i < leftLabels.length; i++) {
            //因为底部需要显示标签所以需要偏移 底部文字高度
            float y = chartRect.bottom - getTextHeight(labelPaint) - part * i;
            // 起点  终点
            if(i == 0) {
                //实线
                canvas.drawLine(chartRect.left,y,chartRect.right,y,linePaint);
            }else {
                //虚线
                dashLinePath.reset();
                dashLinePath.moveTo(chartRect.left,y);
                dashLinePath.lineTo(chartRect.right,y);
                canvas.drawPath(dashLinePath,dashLinePaint);
            }
        }
    }

    public static void main(String[] args) {
       // initChart();
    }


    /**
     * 初始化数组标签
     */
    private void initLabels() {
        Calendar ca=Calendar.getInstance();
        int maximum = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        int minmum=ca.getActualMinimum(Calendar.DAY_OF_MONTH);
        int day=ca.get(Calendar.DAY_OF_MONTH);
        int month = ca.get(Calendar.MONTH) + 1;
        int year = ca.get(Calendar.YEAR);
        //上个月的天数
        int lastMonthDays = getDaysByYearMonth(year,month-1);
        bottomLabels = new String[perNum];
        bottomLabels[0] = month+"月";

        for (int i = 7; i > 0; i--) {
            String strDay;
            if (day < minmum) {
                //若当前日期的前6天不在当前月份的天数内，则需要使用上个月的天数来填补
                if (lastMonthDays < 10 ) {
                    strDay = "0"+lastMonthDays;
                }else {
                    strDay = lastMonthDays +"";
                }
                bottomLabels[i] = strDay;
                //天数减一
                lastMonthDays --;
            }else {
                //若当前日期的前6天还在当前月份的天数内,使用上一天日期即可
                if (day < 10) {
                    strDay = "0" + day;
                }else {
                    strDay = day +"";
                }
                bottomLabels[i] = strDay;
            }
            day --;
        }


        leftLabels = new String[4];

        leftLabels[0] = "0min";
        leftLabels[1] = "2min";
        leftLabels[2] = "4min";
        leftLabels[3] = "6min";

        for (int i = 0;i< bottomLabels.length; i++) {
            System.out.println(bottomLabels[i]);
        }

        for (int i = 0;i< leftLabels.length; i++) {
            System.out.println(">>"+leftLabels[i]);
        }
    }

    /**
     * 左边的标注
     * @param canvas
     */
    private void drawLeftLabels(Canvas canvas) {
        Log.e("drawLeftLabels>>bottom",chartRect.bottom +"");
        //获取控件的高度
        //因为底部需要显示标签所以需要偏移 底部文字高度
        float height = chartRect.bottom - chartRect.top - getTextHeight(labelPaint);
        //标签x坐标
        float labelX = chartRect.left;
        //计算每个标签占的高度
        float part = (float) height / (leftLabels.length );
        float maxLength = getTextWidth(labelPaint,findMaxLength(leftLabels));
        for (int i = 0; i < leftLabels.length; i++) {
            String s = leftLabels[i];
            //获取标签所占的高度
            float labelHeight = getTextHeight(labelPaint);
            //第i个标签的y坐标
            //因为底部需要显示标签所以需要偏移 底部文字高度
            float labelY = chartRect.bottom - getTextHeight(labelPaint) - part * i ;
            if (i == 0) {
                //第0个不做任何操作，主要是为了留出空间
                continue;
            }
            //坐标往下偏移两个文字的高度
            labelY += labelHeight;
            canvas.drawText(s,labelX ,labelY,labelPaint);
        }
    }

    /**
     * 绘制底部标注
     * @param canvas
     */
    private void drawBottomLabels(Canvas canvas) {
        //控件的宽度
        int width = chartRect.right - chartRect.left;
        //根据标签的个数平均分x轴，每一份的宽度
        float partWidth = (float)width / (perNum - 1);
        //y轴坐标
        float labelY = chartRect.bottom;
        for (int i = 0; i < bottomLabels.length; i++) {
            String label = bottomLabels[i];
            //第i个标签的x轴坐标
            float labelX = chartRect.left + partWidth * i;
            //第i个标签的宽度
            float labelWidth = getTextWidth(labelPaint,label);
            float offsetLabelX;
            if (i == 0) {
                offsetLabelX = labelX;
            }else if (i == bottomLabels.length -1) {
                offsetLabelX = chartRect.right - labelWidth;
            }else {
                offsetLabelX = labelX - labelWidth / 2;
            }

            canvas.drawText(label,offsetLabelX,labelY,labelPaint);
        }
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取字符串的长度
     * @param textPaint
     * @param text
     * @return
     */
    public float getTextWidth(Paint textPaint, String text) {
        return textPaint.measureText(text);
    }

    /**
     * 获取字符串的高度
     * @param textPaint
     * @return
     */
    public float getTextHeight(Paint textPaint) {
        //这里获取的两个高度略有不同，height2的高度会略大于height1，
        //这样在文本的顶部和底部就会有一些留白。具体使用哪个高度，要看具体需求了
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float height1 = fm.descent - fm.ascent;
        float height2 = fm.bottom - fm.top;
        return (float) (Math.ceil(height1) -2);
    }

    /**
     * 找出最长的字符串
     * @param arr
     * @return
     */
    private String findMaxLength(String[] arr) {
        String[] newArr = new String[arr.length];
        for (int i =0 ; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        String temp;

        for (int i = 0 ; i < newArr.length; i ++) {
            for (int j = i ; j < newArr.length; j++) {
                //如果第i位置比j+1位置大需要交换位置
                if (newArr[i].length() > newArr[j].length()) {
                    temp = newArr[i];
                    newArr[i] = newArr[j];
                    newArr[j] = temp;
                }
            }
        }
        return newArr[newArr.length-1];
    }
}
