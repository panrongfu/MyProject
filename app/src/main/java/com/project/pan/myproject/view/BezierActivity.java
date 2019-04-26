package com.project.pan.myproject.view;

import android.app.Activity;
import android.os.Bundle;

import com.project.pan.myproject.R;
import com.project.pan.myproject.view.bezier.BezierCurveChart;

import java.util.ArrayList;
import java.util.List;

public class BezierActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        List<BezierCurveChart.Point> points=new ArrayList<BezierCurveChart.Point>();
        for(int i=0;i<10;i++) {
            points.add(new BezierCurveChart.Point(i, (float) (Math.random()*10)));
        }

        BezierCurveChart bezierCurveChart = (BezierCurveChart) findViewById(R.id.bezier_curve_chart);
        String tipText = "3 hours/day on average";
        bezierCurveChart.init(points,new String[] { "0:00", "6:00", "12:00", "18:00", "24:00" }, tipText);
    }
}
