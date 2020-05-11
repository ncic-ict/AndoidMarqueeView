package com.example.andoidmarqueeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        marquee.setText(intent.getStringExtra("content"))
        marquee.setTextSize(intent.getIntExtra("fontSize", 256))
        marquee.setTextColor("#ffffff")
    }
}
