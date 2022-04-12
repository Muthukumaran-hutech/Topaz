package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.topaz.R

class Email_Otp_verification : AppCompatActivity() {

    private var emailloginBtn: Button? = null
    private var backarrow1: ImageView? = null
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_otp_verification)

        emailloginBtn = findViewById(R.id.email_login_btn)
        backarrow1 = findViewById(R.id.backarrow01)
        activity= this


        emailloginBtn?.setOnClickListener{
            startActivity(Intent(activity,LoginActivity::class.java))
            finish()
        }
        backarrow1?.setOnClickListener{
            startActivity(Intent(activity,EmailLogin::class.java))
            finish()
        }
    }
}