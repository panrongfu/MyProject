package com.project.pan.myproject.view.radarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.project.pan.myproject.R;
import com.project.pan.myproject.view.bezier.BezierCurveChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class RadarView extends View {
    private Context mContext;
    private int mWebMode;
    public static final int WEB_MODE_POLYGON = 1;
    public static final int WEB_MODE_CIRCLE = 2;

    public static final int VERTEX_ICON_POSITION_LEFT = 1;
    public static final int VERTEX_ICON_POSITION_RIGHT = 2;
    public static final int VERTEX_ICON_POSITION_TOP = 3;
    public static final int VERTEX_ICON_POSITION_BOTTOM = 4;
    public static final int VERTEX_ICON_POSITION_CENTER = 5;
    private double mPerimeter;

    private float mRadius;
    private PointF mPointCenter;
    private int mLayerLineColor;
    private float mLayerLineWidth;
    private int mVertexLineColor;
    private float mVertexLineWidth;

    private int mLayer;
    private List<Integer> mLayerColor;
    private float mMaxValue;
    private List<Float> mMaxValues;

    private List<String> mVertexText;
    private int mVertexIconPosition;
    private float mVertexIconMargin;

    private int mVertexTextColor;
    private float mVertexTextSize;
    private float mVertexTextOffset;
    private int mMaxVertex;
    private float mCenterTextSize;
    private int mCenterTextColor;

    private double mAngle;

    private List<RadarData> mRadarData;

    private Paint mRadarLinePaint;
    private Paint mLayerPaint;
    private TextPaint mVertexTextPaint;
    private Paint mValuePaint;
    private TextPaint mValueTextPaint;
    private Path mRadarPath;
    private TextPaint mCenterTextPaint;
    private boolean mRotationEnable;

    private String mEmptyHint = "no data";
    private String mMaxLengthVertexText;
    private String mCenterText;
   // private AnimeUtil mAnimeUtil;//
    private float[][] coordArrays = new float[5][5];
    public RadarView(Context context) {
        this(context, null);
    }
    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RadarView);
        mLayer = typedArray.getInt(R.styleable.RadarView_radar_layer, 5);
        mRotationEnable = typedArray.getBoolean(R.styleable.RadarView_rotation_enable, true);
        mWebMode = typedArray.getInt(R.styleable.RadarView_web_mode, WEB_MODE_POLYGON);
        mMaxValue = typedArray.getFloat(R.styleable.RadarView_max_value, 0);
        mLayerLineColor = typedArray.getColor(R.styleable.RadarView_layer_line_color, 0xFF9E9E9E);
        mLayerLineWidth = typedArray.getDimension(R.styleable.RadarView_layer_line_width, dp2px(1));
        mVertexLineColor = typedArray.getColor(R.styleable.RadarView_vertex_line_color, 0xFF9E9E9E);
        mVertexLineWidth = typedArray.getDimension(R.styleable.RadarView_vertex_line_width, dp2px(1));
        mVertexTextColor = typedArray.getColor(R.styleable.RadarView_vertex_text_color, mVertexLineColor);
        mVertexTextSize = typedArray.getDimension(R.styleable.RadarView_vertex_text_size, dp2px(12));
        mVertexTextOffset = typedArray.getDimension(R.styleable.RadarView_vertex_text_offset, 0);
        mCenterTextColor = typedArray.getColor(R.styleable.RadarView_center_text_color, mVertexLineColor);
        mCenterTextSize = typedArray.getDimension(R.styleable.RadarView_center_text_size, dp2px(30));
        mCenterText = typedArray.getString(R.styleable.RadarView_center_text);
        mVertexIconPosition = typedArray.getInt(R.styleable.RadarView_vertex_icon_position, VERTEX_ICON_POSITION_TOP);
        mVertexIconMargin = typedArray.getDimension(R.styleable.RadarView_vertex_icon_margin, 0);
        int vertexTextResid = typedArray.getResourceId(R.styleable.RadarView_vertex_text, 0);
        typedArray.recycle();
        initVertexText(vertexTextResid);
    }

    private void initVertexText(int vertexTextResid) {
        try {
            String[] stringArray = mContext.getResources().getStringArray(vertexTextResid);
            if (stringArray.length > 0) {
                mVertexText = new ArrayList<>();
                Collections.addAll(mVertexText, stringArray);
            }
        } catch (Exception e) {
        }
    }

    private void init() {
        mRadarPath = new Path();
      //  mAnimeUtil = new AnimeUtil(this);
        mRadarData = new ArrayList<>();
        mLayerColor = new ArrayList<>();
        initLayerColor();

        mRadarLinePaint = new Paint();
        mRadarLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mRadarLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLayerPaint = new Paint();
        mValuePaint = new Paint();
        mVertexTextPaint = new TextPaint();
        mValueTextPaint = new TextPaint();
        mCenterTextPaint = new TextPaint();

        mRadarLinePaint.setAntiAlias(true);
        mLayerPaint.setAntiAlias(true);
        mVertexTextPaint.setAntiAlias(true);
        mCenterTextPaint.setAntiAlias(true);
        mValueTextPaint.setAntiAlias(true);
        mValuePaint.setAntiAlias(true);
        mValueTextPaint.setFakeBoldText(true);
    }

    private void initLayerColor() {
        if (mLayerColor == null) {
            mLayerColor = new ArrayList<>();
        }
        if (mLayerColor.size() < mLayer) {
            int size = mLayer - mLayerColor.size();
            for (int i = 0; i < size; i++) {
                mLayerColor.add(Color.TRANSPARENT);
            }
        }
    }

    public int getWebMode() {
        return mWebMode;
    }

    public void setWebMode(int webMode) {
        if (webMode != WEB_MODE_POLYGON && webMode != WEB_MODE_CIRCLE) {
            throw new IllegalStateException("only support WEB_MODE_POLYGON or WEB_MODE_CIRCLE");
        }
        this.mWebMode = webMode;
        invalidate();
    }

    public int getLayer() {
        return mLayer;
    }

    public void setLayer(int layer) {
        this.mLayer = layer;
        initLayerColor();
        invalidate();
    }

    public List<Integer> getLayerColor() {
        return mLayerColor;
    }

    public void setLayerColor(List<Integer> layerColor) {
        this.mLayerColor = layerColor;
        initLayerColor();
        invalidate();
    }

    public int getVertexIconPosition() {
        return mVertexIconPosition;
    }

    public void setVertexIconPosition(int vertexIconPosition) {
        if (vertexIconPosition != VERTEX_ICON_POSITION_BOTTOM
                && vertexIconPosition != VERTEX_ICON_POSITION_CENTER
                && vertexIconPosition != VERTEX_ICON_POSITION_LEFT
                && vertexIconPosition != VERTEX_ICON_POSITION_RIGHT
                && vertexIconPosition != VERTEX_ICON_POSITION_TOP) {
            throw new IllegalStateException("only support VERTEX_ICON_POSITION_BOTTOM" +
                    "  VERTEX_ICON_POSITION_CENTER  VERTEX_ICON_POSITION_LEFT " +
                    " VERTEX_ICON_POSITION_RIGHT  VERTEX_ICON_POSITION_TOP");
        }
        this.mVertexIconPosition = vertexIconPosition;
        invalidate();
    }


    public float getVertexIconMargin() {
        return mVertexIconMargin;
    }

    public void setVertexIconMargin(float margin) {
        this.mVertexIconMargin = margin;
        invalidate();
    }

    public float getVertexTextOffset() {
        return mVertexTextOffset;
    }

    public void setVertexTextOffset(float vertexOffset) {
        this.mVertexTextOffset = vertexOffset;
        invalidate();
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(float maxValue) {
        this.mMaxValue = maxValue;
        this.mMaxValues = null;
        invalidate();
    }

    public List<Float> getMaxValues() {
        return mMaxValues;
    }

    public void setMaxValues(List<Float> maxValues) {
        this.mMaxValues = maxValues;
        initMaxValues();
        invalidate();
    }

    private void initMaxValues() {
        if (mMaxValues != null && mMaxValues.size() < mMaxVertex) {
            int size = mMaxVertex - mMaxValues.size();
            for (int i = 0; i < size; i++) {
                mMaxValues.add(0f);
            }
        }
    }

    public List<String> getVertexText() {
        return mVertexText;
    }

    public void setVertexText(List<String> vertexText) {
        this.mVertexText = vertexText;
        initVertexText();
        invalidate();
    }

    public int getVertexTextColor() {
        return mVertexTextColor;
    }

    public void setVertexTextColor(int vertexTextColor) {
        this.mVertexTextColor = vertexTextColor;
        invalidate();
    }

    public float getVertexTextSize() {
        return mVertexTextSize;
    }

    public void setVertexTextSize(float vertexTextSize) {
        this.mVertexTextSize = vertexTextSize;
        invalidate();
    }

    public void setCenterText(String text) {
        mCenterText = text;
        invalidate();
    }

    public String getCenterText() {
        return mCenterText;
    }

    public float getCenterTextSize() {
        return mCenterTextSize;
    }

    public void setCenterTextSize(float centerTextSize) {
        this.mCenterTextSize = centerTextSize;
        invalidate();
    }

    public int getCenterTextColor() {
        return mCenterTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.mCenterTextColor = centerTextColor;
        invalidate();
    }

    public boolean isRotationEnable() {
        return mRotationEnable;
    }

    public void setRotationEnable(boolean enable) {
        this.mRotationEnable = enable;
    }

    public int getLayerLineColor() {
        return mLayerLineColor;
    }

    public void setLayerLineColor(int color) {
        this.mLayerLineColor = color;
        invalidate();
    }

    public float getLayerLineWidth() {
        return mLayerLineWidth;
    }

    public void setLayerLineWidth(float width) {
        this.mLayerLineWidth = width;
        invalidate();
    }

    public int getVertexLineColor() {
        return mVertexLineColor;
    }

    public void setVertexLineColor(int color) {
        this.mVertexLineColor = color;
        invalidate();
    }

    public float getVertexLineWidth() {
        return mVertexLineWidth;
    }

    public void setVertexLineWidth(float width) {
        this.mVertexLineWidth = width;
        invalidate();
    }

//    public void animeValue(int duration) {
//        for (RadarData radarData : mRadarData) {
//            animeValue(duration, radarData);
//        }
//    }

//    public void animeValue(int duration, RadarData data) {
//        if (!mAnimeUtil.isPlaying(data)) {
//            mAnimeUtil.animeValue(AnimeUtil.AnimeType.ZOOM, duration, data);
//        }
//    }

    public void addData(RadarData data) {
        mRadarData.add(data);
        initData(data);
       // animeValue(2000, data);
    }

    public void setEmptyHint(String hint) {
        mEmptyHint = hint;
        invalidate();
    }

    public String getEmptyHint() {
        return mEmptyHint;
    }

    public void removeRadarData(RadarData data) {
        mRadarData.remove(data);
        invalidate();
    }

    public void clearRadarData() {
        mRadarData.clear();
        invalidate();
    }

    private void initData(RadarData data) {
        List<Float> value = data.getValue();
        float max = Collections.max(value);
        if (mMaxValue == 0 || mMaxValue < max) {
            mMaxValue = max;
        }
        int valueSize = value.size();
        if (mMaxVertex < valueSize) {
            mMaxVertex = valueSize;
        }
        mAngle = 2 * Math.PI / mMaxVertex;
        initVertexText();
        initMaxValues();
    }

    private void initVertexText() {
        if (mVertexText == null || mVertexText.size() == 0) {
            mVertexText = new ArrayList<>();
            for (int i = 0; i < mMaxVertex; i++) {
                char text = (char) ('A' + i);
                mVertexText.add(String.valueOf(text));
            }
        } else if (mVertexText.size() < mMaxVertex) {
            int size = mMaxVertex - mVertexText.size();
            for (int i = 0; i < size; i++) {
                mVertexText.add("");
            }
        }
        if (mVertexText.size() == 0) {
            return;
        }
        mMaxLengthVertexText = Collections.max(mVertexText, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.length() - rhs.length();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRadarData.size() == 0) {
            mValueTextPaint.setTextSize(dp2px(16));
            float hintWidth = mValueTextPaint.measureText(mEmptyHint);
            canvas.drawText(mEmptyHint, mPointCenter.x - hintWidth / 2, mPointCenter.y, mValueTextPaint);
        } else {
            initPaint();
            calcRadius();
            drawRadar(canvas);
            drawDataLayer(canvas);
            drawCenterText(canvas);
        }
    }

    private void initPaint() {
        mRadarLinePaint.setStyle(Paint.Style.STROKE);
        mVertexTextPaint.setColor(mVertexTextColor);
        mVertexTextPaint.setTextSize(mVertexTextSize);
        mLayerPaint.setStyle(Paint.Style.FILL);
        mCenterTextPaint.setTextSize(mCenterTextSize);
        mCenterTextPaint.setColor(mCenterTextColor);
    }

    private void drawRadar(Canvas canvas) {
        if (mWebMode == WEB_MODE_POLYGON) {
            drawWeb(canvas);
        } else if (mWebMode == WEB_MODE_CIRCLE) {
            drawCircle(canvas);
        }
        drawRadarLine(canvas);
    }

    private void drawWeb(Canvas canvas) {
        for (int i = mLayer; i >= 1; i--) {
            float radius = mRadius / mLayer * i;
            int layerColor = mLayerColor.get(i - 1);
            mRadarPath.reset();
            for (int j = 1; j <= mMaxVertex; j++) {
                double angleSin = Math.sin(mAngle * j);
                double angleCos = Math.cos(mAngle * j);
                float x = (float) (mPointCenter.x + angleSin * radius);
                float y = (float) (mPointCenter.y - angleCos * radius);
                if (j == 1) {
                    mRadarPath.moveTo(x, y);
                } else {
                    mRadarPath.lineTo(x, y);
                }
            }
            mRadarPath.close();
            if (layerColor != Color.TRANSPARENT) {
                mLayerPaint.setColor(layerColor);
                canvas.drawPath(mRadarPath, mLayerPaint);
            }
            if (mLayerLineWidth > 0) {
                //设置圆角
               // mRadarLinePaint.setPathEffect(new CornerPathEffect(10));
                mRadarLinePaint.setColor(Color.WHITE);
                mRadarLinePaint.setStrokeWidth(1);
                canvas.drawPath(mRadarPath, mRadarLinePaint);
            }
        }
    }




    private void drawCircle(Canvas canvas) {
        for (int i = mLayer; i >= 1; i--) {
            float radius = mRadius / mLayer * i;
            int layerColor = mLayerColor.get(i - 1);
            if (layerColor != Color.TRANSPARENT) {
                mLayerPaint.setColor(layerColor);
                canvas.drawCircle(mPointCenter.x, mPointCenter.y, radius, mLayerPaint);
            }
            if (mLayerLineWidth > 0) {
                mRadarLinePaint.setColor(mLayerLineColor);
                mRadarLinePaint.setStrokeWidth(mLayerLineWidth);
                canvas.drawCircle(mPointCenter.x, mPointCenter.y, radius, mRadarLinePaint);
            }
        }
    }

    private void drawRadarLine(Canvas canvas) {
        for (int i = 1; i <= mMaxVertex; i++) {
            double angleSin = Math.sin(mAngle * i);
            double angleCos = Math.cos(mAngle * i);
            drawVertex(canvas, i, angleSin, angleCos);
           // drawVertexLine(canvas, angleSin, angleCos);
        }
    }
    //绘制顶点文字
    private void drawVertex(Canvas canvas, int index, double angleSin, double angleCos) {
        float x = (float) (mPointCenter.x + angleSin * (mRadius + mVertexTextOffset));
        float y = (float) (mPointCenter.y - angleCos * (mRadius + mVertexTextOffset));
        String text = mVertexText.get(index - 1);
        float textWidth = mVertexTextPaint.measureText(text);
        Paint.FontMetrics fontMetrics = mVertexTextPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        float textY = y + textHeight / 4;
        float textX = x - textWidth / 2;
        canvas.drawText(text, textX, textY, mVertexTextPaint);
    }

    /**
     * 绘制分割线从中心到个个顶点的分割线
     * @param canvas
     * @param angleSin
     * @param angleCos
     */
    private void drawVertexLine(Canvas canvas, double angleSin, double angleCos) {
        if (mVertexLineWidth <= 0) {
            return;
        }
        float x = (float) (mPointCenter.x + angleSin * mRadius);
        float y = (float) (mPointCenter.y - angleCos * mRadius);
        mRadarLinePaint.setColor(mVertexLineColor);
        mRadarLinePaint.setStrokeWidth(mVertexLineWidth);
        canvas.drawLine(mPointCenter.x, mPointCenter.y, x, y, mRadarLinePaint);
    }

    /**
     * 绘制覆盖区域
     * @param canvas
     */
    private void drawDataLayer(Canvas canvas) {
        for (int i = 0; i < mRadarData.size(); i++) {
            RadarData radarData = mRadarData.get(i);
            mValuePaint.setColor(radarData.getColor());
            mValueTextPaint.setTextSize(radarData.getValueTextSize());
            mValueTextPaint.setColor(radarData.getVauleTextColor());
            List<Float> values = radarData.getValue();
            mRadarPath.reset();
            PointF[] textPoint = new PointF[mMaxVertex];
            for (int j = 1; j <= mMaxVertex; j++) {
                float value = 0;
                if (values.size() >= j) {
                    value = values.get(j - 1);
                }
                Float percent;
                if (mMaxValues != null) {
                    percent = value / mMaxValues.get(j - 1);
                } else {
                    percent = value / mMaxValue;
                }
                if (percent.isInfinite()) {
                    percent = 1f;
                } else if (percent.isNaN()) {
                    percent = 0f;
                }
                if (percent > 1f) {
                    percent = 1f;
                }
                float x = (float) (mPointCenter.x + Math.sin(mAngle * j) * mRadius * percent);
                float y = (float) (mPointCenter.y - Math.cos(mAngle * j) * mRadius * percent);
                coordArrays[j - 1] = new float[]{x, y};
                if (j == 1) {
                    mRadarPath.moveTo(x, y);
                } else {
                    mRadarPath.lineTo(x, y);
                }
                textPoint[j - 1] = new PointF(x, y);

            }
            mRadarPath.close();
            mValuePaint.setAlpha(255);
            mValuePaint.setStyle(Paint.Style.STROKE);
            mValuePaint.setStrokeWidth(radarData.getLineWidth());
            canvas.drawPath(mRadarPath, mValuePaint);
            mValuePaint.setStyle(Paint.Style.FILL);
            mValuePaint.setAlpha(150);
            canvas.drawPath(mRadarPath, mValuePaint);
//            if (radarData.isValueTextEnable()) {
//                List<String> valueText = radarData.getValueText();
//                for (int k = 0; k < textPoint.length; k++) {
//                    String text = "";
//                    if (valueText.size() > k) {
//                        text = valueText.get(k);
//                    }
//                    float textWidth = mValueTextPaint.measureText(text);
//                    Paint.FontMetrics fontMetrics = mValueTextPaint.getFontMetrics();
//                    float textHeight = fontMetrics.descent - fontMetrics.ascent;
//                    canvas.drawText(text, textPoint[k].x - textWidth / 2, textPoint[k].y + textHeight / 3, mValueTextPaint);
//                }
//            }

            for (int m = 0 ; m <= 4; m++) {
                for (int n = 4; n  >=1; n --) {
                    float radius = 12 / 3 * n;
                    mLayerPaint.setColor(Color.RED);
                    canvas.drawCircle(coordArrays[m][0], coordArrays[m][1], radius, mLayerPaint);
                }
            }
        }
    }

    private void drawCenterText(Canvas canvas) {
        if (!TextUtils.isEmpty(mCenterText)) {
            float textWidth = mCenterTextPaint.measureText(mCenterText);
            Paint.FontMetrics fontMetrics = mCenterTextPaint.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;
            canvas.drawText(mCenterText, mPointCenter.x - textWidth / 2, mPointCenter.y + textHeight / 3, mCenterTextPaint);
        }
    }

    private void calcRadius() {
        if (mVertexText == null || mVertexText.size() == 0) {
            mRadius = Math.min(mPointCenter.x, mPointCenter.y) - mVertexTextOffset;
        } else {
            float maxWidth = mVertexTextPaint.measureText(mMaxLengthVertexText) / 2;
            mRadius = Math.min(mPointCenter.x, mPointCenter.y) - (maxWidth + mVertexTextOffset);
            mPerimeter = 2 * Math.PI * mRadius;
        }
    }
private String[] name = new String[]{"力量", "敏捷", "速度", "智力", "精神", "耐力"};
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
              //"力量", "敏捷", "速度", "智力", "精神", "耐力"

                for (int n = 0; n <= 4; n++) {
                    float currentX = coordArrays[n][0];
                    float currentY = coordArrays[n][1];
                    if (currentX - downX < 5 && currentY - downY < 5) {
                        Toast.makeText(mContext,name[n],Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        if (!mRotationEnable) return super.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPointCenter = new PointF(w / 2, h / 2);
    }

    private float dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
