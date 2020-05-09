package com.example.andoidmarqueeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        marquee.setText("白百何方否认参加《乘风破浪的姐姐》，自称“不想冲浪，晕船”")
        marquee.setTextSize(20)
        marquee.setTextColor("#ffffff")
    }
}
