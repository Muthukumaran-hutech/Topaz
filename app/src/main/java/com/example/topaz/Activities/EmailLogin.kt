package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.topaz.R
import com.hbb20.CountryCodePicker

class EmailLogin : AppCompatActivity() {

    private var continueBtn: Button? = null

    private var email: EditText? = null
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_login)

        continueBtn = findViewById(R.id.email_continue_btn)
        email = findViewById(R.id.email)
        activity = this

        continueBtn?.setOnClickListener{
            startActivity(Intent(activity,Email_Otp_verification::class.java))
            finish()
        }
    }
}