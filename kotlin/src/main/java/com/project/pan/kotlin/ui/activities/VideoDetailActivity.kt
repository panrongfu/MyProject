package com.project.pan.kotlin.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.project.pan.kotlin.R

class VideoDetailActivity : AppCompatActivity() {

    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
}
