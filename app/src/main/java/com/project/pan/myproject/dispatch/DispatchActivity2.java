package com.project.pan.myproject.dispatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.pan.myproject.R;

import java.util.ArrayList;

public class DispatchActivity2 extends AppCompatActivity {

    HorizontalScrollViewEx2 horizontalScrollViewEx2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch2);
        initView();
    }

    private void initView() {
        horizontalScrollViewEx2  = findViewById(R.id.container);
        for(int i=0; i<3; i++){
            ViewGroup layout = (ViewGroup) LayoutInflater.from(this)
                    .inflate(R.layout.dispatch_item_layout2,horizontalScrollViewEx2,false);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            // heigth = dm.heightPixels;
            int width = dm.widthPixels;
            layout.getLayoutParams().width = width;
            createList(layout);
            horizontalScrollViewEx2.addView(layout);
        }
    }

    private void createList(View layout) {
        ListView listView = layout.findViewById(R.id.item_listViewEx);
        ArrayList<String> datas = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            datas.add("name"+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.content_layout_list_item,
                R.id.name,
                datas);
        listView.setAdapter(adapter);
    }
}
