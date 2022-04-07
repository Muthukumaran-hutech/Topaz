package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.topaz.R

class OtpVerfification : AppCompatActivity() {

    private var loginBtn: Button? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verfification)

        loginBtn = findViewById(R.id.login_btn)
        activity = this

        loginBtn?.setOnClickListener{
            startActivity(Intent(activity,AccountInformation::class.java))
            finish()
        }
    }
}