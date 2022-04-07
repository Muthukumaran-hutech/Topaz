package com.example.topaz.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.topaz.Fragments.SliderFragment
import com.example.topaz.R

class SplashScreen : AppCompatActivity() {

    private var topazImage: ImageView? = null
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        topazImage = findViewById(R.id.topaz)
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, IntroSlider::class.java)
            startActivity(intent)
            finish()
        },3000)  //Delaying 3 seconds to open IntroPage


    }
}