package com.project.pan.myproject.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.project.pan.myproject.R;


/**
 * @Author: panrongfu
 * @CreateDate: 2019/5/22 16:49
 * @Description: java类作用描述
 */
public class CenterLayoutAnimView extends FrameLayout {

    private VideoView videoView;
    private ImageView jieTiFrameIv;
    private ImageView xuanZhuanIv;
    private ImageView neiQuanIv;

    public CenterLayoutAnimView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CenterLayoutAnimView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CenterLayoutAnimView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context,R.layout.layout_center_anim,this);
        videoView =  findViewById(R.id.videoView);
        jieTiFrameIv = findViewById(R.id.jie_ti_frame_iv);
        xuanZhuanIv =  findViewById(R.id.xuan_zhuan_iv);
        neiQuanIv = findViewById(R.id.nei_quan_layer);
        setOnClickListener(v -> showClickAnim());
    }

    /**
     * 切换页面显示动画效果
     */
    public void showSwitchPageAnim(){
        //视频
        String uri = "android.resource://" + getContext().getPackageName() + "/" + R.raw.kuosanquan;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.setVisibility(INVISIBLE);
            }
        });
        videoView.start();
        //内旋圈
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(neiQuanIv, "rotation", 180f);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        objectAnimator.setRepeatMode(ValueAnimator.INFINITE);//
        objectAnimator.start();

        //阶梯
        AnimationDrawable anim = (AnimationDrawable) jieTiFrameIv.getBackground();
        anim.start();

    }

    /**
     * 点击效果
     */
    public void showClickAnim() {
        //外旋框

        ValueAnimator animator = ValueAnimator.ofFloat(0, 120f);
        animator.setDuration(2000);
        animator.addUpdateListener(valueAnimator -> {
            float degree = (float) valueAnimator.getAnimatedValue();
            xuanZhuanIv.setRotation(degree);
        });
        animator.start();

        //扩散效果
        String uri = "android.resource://" + getContext().getPackageName() + "/" + R.raw.kuosanquan;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();

        //阶梯
        AnimationDrawable anim = (AnimationDrawable) jieTiFrameIv.getBackground();
        anim.start();
    }
}
