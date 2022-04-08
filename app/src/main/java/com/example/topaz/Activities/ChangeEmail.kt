package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.topaz.R

class ChangeEmail : AppCompatActivity() {

    private var backarrow1: ImageView? = null
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        backarrow1 = findViewById<ImageView>(R.id.backarrow1)
        activity = this


        backarrow1?.setOnClickListener{
            startActivity(Intent(activity,EditProfile::class.java))
            finish()
        }
    }
}