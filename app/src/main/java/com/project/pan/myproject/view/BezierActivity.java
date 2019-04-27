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
//        for(int i=0;i<10;i++) {
//            points.add(new BezierCurveChart.Point(i, (float) (Math.random()*10)));
//        }

        points.add(new BezierCurveChart.Point(0,10));
        points.add(new BezierCurveChart.Point(1,20));

        points.add(new BezierCurveChart.Point(2,10));

        points.add(new BezierCurveChart.Point(3,20));

        points.add(new BezierCurveChart.Point(4,80));

        points.add(new BezierCurveChart.Point(5,20));

        points.add(new BezierCurveChart.Point(6,70));

        points.add(new BezierCurveChart.Point(7,20));

        points.add(new BezierCurveChart.Point(8,50));

        points.add(new BezierCurveChart.Point(9,70));

        points.add(new BezierCurveChart.Point(10,90));

        BezierCurveChart bezierCurveChart = (BezierCurveChart) findViewById(R.id.bezier_curve_chart);
       // String tipText = "3 hours/day on average";
        //new String[]{"糟糕","差","一般","良","优"}
        //new int[] {20,40,60,70,80}
        bezierCurveChart.init(points,new String[] { "0:00", "6:00", "12:00", "18:00", "24:00" },new String[]{"糟糕","差","一般","良","优"}, null);
    }
}
