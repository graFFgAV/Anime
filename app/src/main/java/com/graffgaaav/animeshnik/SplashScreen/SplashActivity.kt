package com.graffgaaav.animeshnik.SplashScreen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextPaint
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.blue
import com.graffgaaav.animeshnik.MainActivity
import com.graffgaaav.animeshnik.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private var radius = 10f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        (0..250).map {
            DotTagItem(resources.getDimension(R.dimen.dot_radius_2))
        }.toList().let {
            tagSphere1.addTagList(it)
        }

        val intent = Intent(this, MainActivity::class.java)
        object : CountDownTimer(2000, 20) {
            override fun onTick(millisUntilFinished: Long) {
                radius -= 0.10f
                tagSphere1.setRadius(radius)
            }
            override fun onFinish() {
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        tagSphere1.startAutoRotation(-5f, 5f)
    }

    override fun onPause() {
        super.onPause()
        tagSphere1.stopAutoRotation()
    }
}