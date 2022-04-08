package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.topaz.R

class FaqActivity : AppCompatActivity() {

    private var backarrow: ImageView? = null
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)


        backarrow = findViewById<ImageView>(R.id.backarrow)
        activity = this



        backarrow?.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }
    }
}