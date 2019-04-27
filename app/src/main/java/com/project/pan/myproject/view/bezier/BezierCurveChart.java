package com.project.pan.myproject.view.bezier;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BezierCurveChart extends View {

    public static class Point {
        //根据时间坐标x进行点的排序
        public static final Comparator<Point> X_COMPARATOR = new Comparator<Point>() {
            @Override
            public int compare(Point lhs, Point rhs) {
                return (int) (lhs.x * 1000 - rhs.x * 1000);
            }
        };

        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Point() {
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    private static final float CURVE_LINE_WIDTH = 4f;
    private static final float HALF_TIP_HEIGHT = 16;

    private static final String TAG = BezierCurveChart.class.getSimpleName();

    private Point[] adjustedPoints;
    private Paint borderPaint = new Paint();
    private Paint chartBgPaint = new Paint();
    // The rect of chart, x bottomLabels on the bottom are not included
    private Rect chartRect = new Rect();
    private Paint curvePaint = new Paint();
    private Path curvePath = new Path();
    private Paint fillPaint = new Paint();
    private Path fillPath = new Path();

    private Paint gridPaint = new Paint();
    private Paint labelPaint = new Paint();
    private String[] bottomLabels;
    private String[] leftLables;
    private float maxY;

    private List<Point> originalList;
    private float scaleY;
    private Rect textBounds = new Rect();
    private Paint tipLinePaint = new Paint();
    private Paint tipPaint = new Paint();
    private Rect tipRect = new Rect();
    private RectF tipRectF = new RectF();
    private String tipText;
    private Paint tipTextPaint = new Paint();

    public BezierCurveChart(Context context) {
        super(context);
    }

    public BezierCurveChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(List<Point> originalList, String[] bottomLabels, String[] leftLables, String tipText) {

        //边框画笔
        borderPaint.setColor(Color.DKGRAY);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeCap(Paint.Cap.SQUARE);
        borderPaint.setStrokeWidth(4.0f);
        borderPaint.setAntiAlias(true);
        //曲线画笔
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setStrokeCap(Paint.Cap.ROUND);
        curvePaint.setStrokeWidth(CURVE_LINE_WIDTH);
        curvePaint.setColor(Color.RED);
        curvePaint.setAntiAlias(true);
        curvePaint.setAlpha(200);

        //面积填充画笔
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.GRAY);
        fillPaint.setAlpha(200);
        fillPaint.setAntiAlias(true);

        //背景画笔
        chartBgPaint.setStyle(Paint.Style.FILL);
        chartBgPaint.setColor(Color.GREEN);
        chartBgPaint.setAlpha(180);
        chartBgPaint.setAntiAlias(true);

        //竖线画笔
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeCap(Paint.Cap.SQUARE);
        gridPaint.setColor(Color.BLUE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(1.5f);

        //提示文字横线的画笔
        tipLinePaint.setStyle(Paint.Style.STROKE);
        tipLinePaint.setStrokeCap(Paint.Cap.SQUARE);
        tipLinePaint.setStrokeWidth(1.5f);
        tipLinePaint.setColor(Color.WHITE);
        tipLinePaint.setAntiAlias(true);
        tipLinePaint.setAlpha(220);

        //提示文字背景画笔
        tipPaint.setStyle(Paint.Style.FILL);
        tipPaint.setColor(Color.YELLOW);
        tipPaint.setAntiAlias(true);

        //提示文字画笔
        tipTextPaint.setColor(Color.WHITE);
        tipTextPaint.setTextSize(21f);
        tipTextPaint.setAntiAlias(true);

        //底部文字画笔
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(21f);
        labelPaint.setAntiAlias(true);

        this.originalList = originalList;
        this.bottomLabels = bottomLabels;
        this.leftLables = leftLables;
        this.tipText = tipText;
        adjustedPoints = new Point[originalList.size()];
        // order by x coodinate ascending
        Collections.sort(originalList, Point.X_COMPARATOR);
        super.invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //获取View的绘制范围，即左、上、右、下边界相对于此View的左顶点的距离（偏移量），即0、0、View的宽、View的高
        getDrawingRect(chartRect);

        Log.d(TAG, chartRect.toString());

        if (originalList != null) {
            // mockPoints(width, height);
            drawBottomLabels(canvas);
            drawLeftBales(canvas);
            int chartHeight = chartRect.bottom - chartRect.top;
            int chartWidth = chartRect.right - chartRect.left;

            adjustPoints(chartWidth, chartHeight);

            drawGrid(canvas, chartWidth);
            drawCurve(canvas, chartWidth, chartHeight);

            if (tipText != null) {
                drawTip(canvas, chartWidth, chartHeight);
            }
            canvas.drawRect(chartRect, borderPaint);
        }
    }
    
    private void adjustPoints(int chartWidth, int chartHeight) {
        maxY = 0;
        // find max y coodinate
        for (Point p : originalList) {
            if (p.y > maxY) {
                maxY = p.y;
            }
        }

        //Y coodinate sacle
        scaleY = chartHeight / maxY;
        //轴线跨度 终点和起点直接的跨度
        float horizontalAxesSpan = originalList.get(originalList.size() - 1).x - originalList.get(0).x;
        //跨度0-100
        float verticalAxesSpan = 99f;
        //第一个点的x坐标
        float startX = originalList.get(0).x;
        //第一个点的y坐标
        float startY = originalList.get(0).y;
        Log.e("adjustPoints","scaleY:"+scaleY +"horizontalAxesSpan:"+horizontalAxesSpan+"startX:"+startX+"startY:"+startY);

        //new String[]{"糟糕","差","一般","良","优"}
        //new int[] {20,40,60,70,80}
        for (int i = 0; i < originalList.size(); i++) {
            Point p = originalList.get(i);

            Point newPoint = new Point();
            //p.x - startX => 第i个点距离0点所占的跨度
            //chartWidth / axesSpan => 每个跨度在屏幕中所占的宽度
            //(p.x - startX) * chartWidth / axesSpan + chartRect.left => 第i个点的x坐标
            newPoint.x = (p.x - startX) * chartWidth / horizontalAxesSpan + chartRect.left;
            //第i个点的y跨度 * 每个跨度在屏幕显示的高度 + chartRect距离顶部的距离= 第i个点所在的y坐标
            newPoint.y = p.y * chartHeight / verticalAxesSpan + chartRect.top;
            //newPoint.y = chartHeight - newPoint.y;
            Log.e("p.y",p.y+"");
            adjustedPoints[i] = newPoint;
        }
    }

    /**
     * 创建路径，最重要的部分
     * @param path
     */
    private void buildPath(Path path) {
        path.reset();

        path.moveTo(adjustedPoints[0].x, adjustedPoints[0].y);
        int pointSize = adjustedPoints.length;

        for (int i = 0; i < adjustedPoints.length - 1; i++) {
            float pointX = (adjustedPoints[i].x + adjustedPoints[i + 1].x) / 2;
            float pointY = (adjustedPoints[i].y + adjustedPoints[i + 1].y) / 2;

            float controlX = adjustedPoints[i].x;
            float controlY = adjustedPoints[i].y;

            path.quadTo(controlX, controlY, pointX, pointY);
        }
        path.quadTo(adjustedPoints[pointSize - 1].x, adjustedPoints[pointSize - 1].y, adjustedPoints[pointSize - 1].x,
                adjustedPoints[pointSize - 1].y);
    }

    /**
     * 绘制表格
     * @param canvas
     * @param width
     * @param height
     */
    private void drawCurve(Canvas canvas, float width, float height) {
        buildPath(curvePath);
        buildPath(fillPath);

        fillPath.lineTo(chartRect.right, chartRect.bottom);
        fillPath.lineTo(chartRect.left, chartRect.bottom);
        fillPath.lineTo(chartRect.left, adjustedPoints[0].y);
        fillPath.close();

        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(curvePath, curvePaint);
    }

    /**
     * 绘制网格 上下左右都有边框 则不需要在绘制
     * @param canvas
     * @param width
     */
    private void drawGrid(Canvas canvas, int width) {
        canvas.drawRect(chartRect, chartBgPaint);
        //绘制竖线
        int verticalCount = bottomLabels.length-1;
        float vPart = (float) width / verticalCount;
        for (int i = 1; i < verticalCount; i++) {
            float x = chartRect.left + vPart * i;
            canvas.drawLine(x, chartRect.top, x, chartRect.bottom, gridPaint);
        }

        //绘制水平线
        int horizontalCount = leftLables.length-1;
        float height = chartRect.bottom - chartRect.top;
        float hPart =  height / horizontalCount;
        for (int i = 1; i < horizontalCount; i++) {
            float y = chartRect.bottom - hPart * i;
            // 起点  终点
            canvas.drawLine(chartRect.left,y,chartRect.right,y,gridPaint);
        }
    }

    /**
     * 绘制底部的标签
     * @param canvas
     */
    private void drawBottomLabels(Canvas canvas) {
        //获取控件的宽度
        int width = chartRect.right - chartRect.left;
        //label 坐标y
        float labelY = chartRect.bottom;
        //根据控件的宽度平均分每个label所占的宽度
        float part = (float) width / (bottomLabels.length - 1);

        for (int i = 0; i < bottomLabels.length; i++) {
            String s = bottomLabels[i];
            //第i个的起始位置x
            float centerX = chartRect.left + part * i;
            float labelWidth = getTextWidth(labelPaint, s);
            float labelX;
            if (i == 0) {
                labelX = chartRect.left;
            } else if (i == bottomLabels.length - 1) {
                //为了文字不被挡住，x值需要偏移整个文字的宽度
                labelX = chartRect.right - labelWidth;
            } else {
                //为了文字居中，需要向左便宜文字宽度的一半
                labelX = centerX - labelWidth / 2;
            }
            labelX += getTextWidth(labelPaint,findMaxLength(leftLables))/2;
            canvas.drawText(s, labelX, labelY, labelPaint);
        }
        //这里主要是让label有显示的空间
        chartRect.bottom = (int) (chartRect.bottom - getTextHeight(labelPaint) * 1.2);
    }


    /**
     * 绘制左半边的标签
     * @param canvas
     */
    private void drawLeftBales(Canvas canvas) {
        //获取表格的高度
        int height = chartRect.bottom - chartRect.top;

        //标签x坐标
        float labelX = chartRect.left;

        //计算每个标签占的高度
        float part = (float) height / (leftLables.length -1);

        for (int i = 0; i < leftLables.length; i++) {
            String s = leftLables[i];
            //第i个标签的y坐标
            float centerY = chartRect.bottom - part * i;
            //获取标签所占的高度
            float labelHeight = getTextHeight(labelPaint);
            float labelY;
            if (i == 0) {
                //当标签是第一个时候，坐标就是表格的y坐标
                labelY = chartRect.bottom;
            }else if (i == leftLables.length - 1) {
                //当标签为最后一个时候，等于表格的top，为了能够显示完全，需要向下偏移labelHeight
                labelY = chartRect.top + labelHeight;
            }else {
                //这里就是为了显示居中
                labelY = centerY - labelHeight/2;
            }
            Log.e("labelY",labelY+"");
            canvas.drawText(s,labelX,labelY,labelPaint);
        }

        //这里需要找出最长的字符串
        chartRect.left = (int)((chartRect.left + getTextWidth(labelPaint,findMaxLength(leftLables))));
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

    private void drawTip(Canvas canvas, float width, float height) {

        float totalHeight = 0;
        for (Point p : adjustedPoints) {
            totalHeight += p.y;
        }

        float tipLineY = totalHeight / adjustedPoints.length;

        if (tipLineY + HALF_TIP_HEIGHT >= chartRect.bottom) {
            tipLineY = chartRect.bottom - HALF_TIP_HEIGHT - 4;
        }

        String text = tipText;
        tipTextPaint.getTextBounds(text, 0, 1, textBounds);

        float centerX = chartRect.left + width / 2;
        float textWidth = getTextWidth(tipTextPaint, text);

        tipRect.left = (int) (centerX - textWidth / 2 - 23);
        tipRect.right = (int) (centerX + textWidth / 2 + 23);
        tipRect.top = (int) (tipLineY - HALF_TIP_HEIGHT);
        tipRect.bottom = (int) (tipLineY + HALF_TIP_HEIGHT);

        tipRectF.set(tipRect);

        float textX = centerX - textWidth / 2;
        int textHeight = textBounds.bottom - textBounds.top;

        float textY = tipLineY + textHeight / (float) 2 - 3;

        canvas.drawLine(chartRect.left, tipLineY, chartRect.right, tipLineY, tipLinePaint);
        canvas.drawRoundRect(tipRectF, HALF_TIP_HEIGHT, HALF_TIP_HEIGHT, tipPaint);
        canvas.drawText(text, textX, textY, tipTextPaint);
    }

    public float getTextHeight(Paint textPaint) {
        FontMetrics fm = textPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent) - 2;
    }

    public float getTextWidth(Paint textPaint, String text) {
        return textPaint.measureText(text);
    }

    public Paint getBorderPaint() {
        return borderPaint;
    }

    public Paint getChartBgPaint() {
        return chartBgPaint;
    }

    public Paint getCurvePaint() {
        return curvePaint;
    }

    public Paint getFillPaint() {
        return fillPaint;
    }

    public Paint getGridPaint() {
        return gridPaint;
    }

    public Paint getLabelPaint() {
        return labelPaint;
    }

    public Paint getTipLinePaint() {
        return tipLinePaint;
    }

    public Paint getTipPaint() {
        return tipPaint;
    }

    public Paint getTipTextPaint() {
        return tipTextPaint;
    }

    public void setBorderPaint(Paint borderPaint) {
        this.borderPaint = borderPaint;
    }

    public void setChartBgPaint(Paint chartBgPaint) {
        this.chartBgPaint = chartBgPaint;
    }

    public void setCurvePaint(Paint curvePaint) {
        this.curvePaint = curvePaint;
    }

    public void setFillPaint(Paint fillPaint) {
        this.fillPaint = fillPaint;
    }

    public void setGridPaint(Paint gridPaint) {
        this.gridPaint = gridPaint;
    }

    public void setLabelPaint(Paint labelPaint) {
        this.labelPaint = labelPaint;
    }

    public void setTipLinePaint(Paint tipLinePaint) {
        this.tipLinePaint = tipLinePaint;
    }

    public void setTipPaint(Paint tipPaint) {
        this.tipPaint = tipPaint;
    }

    public void setTipTextPaint(Paint tipTextPaint) {
        this.tipTextPaint = tipTextPaint;
    }
}
