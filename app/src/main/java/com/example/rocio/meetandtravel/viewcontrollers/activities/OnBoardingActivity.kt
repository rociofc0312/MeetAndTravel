package com.example.rocio.meetandtravel.viewcontrollers.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.rocio.meetandtravel.R
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    private val LAUNCH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        var launchAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_launch)
        logoImageView.startAnimation(launchAnimation)
        appText.startAnimation(launchAnimation)
        companyImageView.startAnimation(launchAnimation)
        Handler().postDelayed(object:Runnable{
            override fun run() {
                finish()
                val loginActivity = Intent(this@OnBoardingActivity,MainActivity::class.java)
                startActivity(loginActivity)
            }
        },LAUNCH_TIME_OUT.toLong())
    }
}
