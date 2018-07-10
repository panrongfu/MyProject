package com.project.pan.myproject.view.textSwithcer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.project.pan.myproject.R;

public class TextSwitcherActivity extends AppCompatActivity {

    AdvTextSwitcher advTextSwitcher;
    private Switcher switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switcher);

        //For example, the String array below contains four recent reviews.
        String[] texts = {"Anne: Great!", "Cathy: I do not think so.", "Jimmy: Cloning your repo...", "Aoi: This bug disappeared!"};
        advTextSwitcher = (AdvTextSwitcher) findViewById(R.id.advTextSwitcher);
        //Give them to AdvTextSwitcher
        advTextSwitcher.setTexts(texts);

        //Auto switch between texts every 5000ms.
        switcher = new Switcher(advTextSwitcher, 3);
        switcher.start();
        advTextSwitcher.setOnTipClickListener(new AdvTextSwitcher.OnTipClickListener() {
            @Override
            public void onTipClick(int position) {
                Toast.makeText(TextSwitcherActivity.this,"click:"+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void stopBtnClick(View view){
        switcher.pause();
    }

    public void startBtnClick(View view){
        switcher.start();
    }
}
