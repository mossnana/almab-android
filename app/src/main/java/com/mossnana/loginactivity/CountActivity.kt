package com.mossnana.loginactivity

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_count.*

class CountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_count)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        pointLeft.setOnClickListener {
            var currentPointLeft = pointLeft.text.toString().toInt()
            pointLeft.setText((currentPointLeft+1).toString())
        }

        pointRight.setOnClickListener {
            var currentPointRight = pointRight.text.toString().toInt()
            pointRight.setText((currentPointRight+1).toString())
        }

        btnReset.setOnClickListener {
            val resetScore: Int = 0
            pointLeft.setText((resetScore).toString())
            pointRight.setText((resetScore).toString())
        }
    }
}
