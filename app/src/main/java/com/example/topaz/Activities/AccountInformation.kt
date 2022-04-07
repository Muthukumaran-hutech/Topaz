package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.topaz.R

class AccountInformation : AppCompatActivity() {

    private var updateBtn: Button? = null
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_information)

        updateBtn = findViewById(R.id.update)
        activity = this

        updateBtn?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }
}