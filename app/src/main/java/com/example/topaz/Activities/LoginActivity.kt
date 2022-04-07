package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.topaz.R
import com.hbb20.CountryCodePicker

class LoginActivity : AppCompatActivity() {

    private var continueBtn: Button? = null
    private var countryCode: CountryCodePicker? = null
    private var phoneNo: EditText? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        continueBtn = findViewById(R.id.continue_btn)
        countryCode = findViewById(R.id.countryCodePicker)
        phoneNo = findViewById(R.id.phone_no)
        activity = this

        continueBtn?.setOnClickListener{
            startActivity(Intent(activity,OtpVerfification::class.java))
            finish()
        }

    }
}