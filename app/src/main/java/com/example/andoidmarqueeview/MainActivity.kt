package com.example.andoidmarqueeview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startPlay.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            intent.putExtra("content", "苍老师，我爱你")
            intent.putExtra("fontSize", 500)
            startActivity(intent)
        }
    }
}
