package com.project.pan.myproject.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.pan.myproject.R;
import com.project.pan.myproject.view.radarView.RadarData;
import com.project.pan.myproject.view.radarView.RadarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class RadarActivity extends AppCompatActivity {

    private RadarView mRadarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        mRadarView = (RadarView) findViewById(R.id.radarView);

        mRadarView.setEmptyHint("无数据");

        List<Integer> layerColor = new ArrayList<>();
      //  Collections.addAll(layerColor, 0x3300bcd4, 0x3303a9f4, 0x335677fc, 0x333f51b5, 0x33673ab7);
        //从里到外
            Collections.addAll(layerColor, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT,0x33673ab7);

        mRadarView.setLayerColor(layerColor);

        List<String> vertexText = new ArrayList<>();
        Collections.addAll(vertexText, "力量", "敏捷", "速度", "智力", "精神", "耐力");
        mRadarView.setVertexText(vertexText);

//        List<Integer> res = new ArrayList<>();
//        Collections.addAll(res, R.mipmap.power, R.mipmap.agile, R.mipmap.speed,
//                R.mipmap.intelligence, R.mipmap.spirit);
//        mRadarView.setVertexIconResid(res);

//        List<Float> values = new ArrayList<>();
//        Collections.addAll(values, 3f, 6f, 2f, 7f, 5f);
//        RadarData data = new RadarData(values,Color.WHITE);
//        mRadarView.addData(data);

        List<Float> values2 = new ArrayList<>();
        Collections.addAll(values2, 7f, 1f, 4f, 2f, 8f);
        RadarData data2 = new RadarData(values2,Color.WHITE);
        data2.setValueTextEnable(true);
        data2.setVauleTextColor(Color.WHITE);
        data2.setValueTextSize(dp2px(10));
        data2.setLineWidth(dp2px(1));
        mRadarView.addData(data2);

    }
}
