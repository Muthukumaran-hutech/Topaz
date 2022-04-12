package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.topaz.R

class AccountInformation : AppCompatActivity() {

    private var updateBtn: Button? = null
    private var backarrow: ImageView? = null
    private var skipbtn: TextView? = null
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_information)

        updateBtn = findViewById(R.id.update)
        backarrow = findViewById(R.id.imageViewback)
        skipbtn = findViewById(R.id.sk_ip)
        activity = this

        updateBtn?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }

        updateBtn?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }

        backarrow?.setOnClickListener{
            startActivity(Intent(activity,LoginActivity::class.java))
            finish()
        }
    }
}