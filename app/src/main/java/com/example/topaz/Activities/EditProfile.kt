package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.topaz.R

class EditProfile : AppCompatActivity() {

    private var backarrow1: ImageView? = null
    private var profile_change: ImageView? = null
    private var change_Email: TextView? = null
    private var change_Phoneno: TextView? = null
    private var change_Pwd: TextView? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        profile_change = findViewById<ImageView>(R.id.edit_image)
        change_Email = findViewById<TextView>(R.id.change1)
        change_Phoneno = findViewById<TextView>(R.id.change2)
        change_Pwd = findViewById<TextView>(R.id.change3)
        backarrow1 = findViewById<ImageView>(R.id.backarrow1)
        activity = this

        profile_change?.setOnClickListener{
            Toast. makeText(applicationContext," Currently In process ", Toast. LENGTH_SHORT).show()

        }


        change_Email?.setOnClickListener{
            startActivity(Intent(activity,ChangeEmail::class.java))
            finish()
        }

        change_Phoneno?.setOnClickListener{
            startActivity(Intent(activity,ChangePhoneNo::class.java))
            finish()
        }

        change_Pwd?.setOnClickListener{
            startActivity(Intent(activity,NewPassword::class.java))
            finish()
        }

        backarrow1?.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }
    }
}