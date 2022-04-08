package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.topaz.R

class SearchActivity : AppCompatActivity() {

    private var backarrow3: ImageView? = null
    private var backhome: Button? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backarrow3 = findViewById<ImageView>(R.id.backarrow3)
        backhome = findViewById<Button>(R.id.backhome)
        activity = this


        backhome?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }
}