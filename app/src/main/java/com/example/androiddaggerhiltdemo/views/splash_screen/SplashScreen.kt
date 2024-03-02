package com.example.androiddaggerhiltdemo.views.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import com.example.androiddaggerhiltdemo.databinding.ActivitySplashScreenBinding
import com.example.androiddaggerhiltdemo.views.home_screen.NewsViewActivity

class SplashScreen : ComponentActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreen, NewsViewActivity::class.java))
            finish()
        },2000)
    }
}
