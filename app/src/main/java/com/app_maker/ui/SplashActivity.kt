package com.app_maker.ui

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.app_maker.R
import com.app_maker.databinding.ActivitySplashActibityBinding

class SplashActivity : AppCompatActivity() {
    private val handler = Handler()
    private lateinit var binding : ActivitySplashActibityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashActibityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initImageView()
    }

    private fun initImageView() {
        binding.splashImg.animate().rotation(1080f).scaleX(1f)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(3000)
            .setListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animator: Animator?) {
                }
                override fun onAnimationEnd(animator: Animator?) {
                    handler.postDelayed({
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }, 3000)
                }
                override fun onAnimationCancel(animator: Animator?) {
                }
                override fun onAnimationRepeat(animator: Animator?) {
                }
            })
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}