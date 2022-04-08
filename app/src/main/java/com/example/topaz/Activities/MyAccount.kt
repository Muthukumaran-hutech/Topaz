package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.topaz.R

class MyAccount : AppCompatActivity() {

    private var profile_image: ImageView? = null
    private var editemailicon: ImageView? = null
    private var myorderprofile: LinearLayout? = null
    private var myaddressprofile: LinearLayout? = null
    private var notificationsprofile: LinearLayout? = null
    private var helpprofile: LinearLayout? = null
    private var logoutprofile: LinearLayout? = null
    private var backarrow1: ImageView? = null
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)


        profile_image = findViewById<ImageView>(R.id.profile_image)
        editemailicon = findViewById<ImageView>(R.id.editemailicon)
        myorderprofile = findViewById<LinearLayout>(R.id.myorderprofile)
        myaddressprofile = findViewById<LinearLayout>(R.id.myaddressprofile)
        notificationsprofile = findViewById<LinearLayout>(R.id.notificationsprofile)
        helpprofile = findViewById<LinearLayout>(R.id.helpprofile)
        logoutprofile = findViewById<LinearLayout>(R.id.logoutprofile)
        backarrow1 = findViewById<ImageView>(R.id.backarrow1)
        activity = this


        profile_image?.setOnClickListener{
            startActivity(Intent(activity,EditProfile::class.java))
            finish()
        }


        editemailicon?.setOnClickListener{
            startActivity(Intent(activity,ChangeEmail::class.java))
            finish()
        }

        myorderprofile?.setOnClickListener{
            startActivity(Intent(activity,MyOrders::class.java))
            finish()
        }

        myaddressprofile?.setOnClickListener{
            startActivity(Intent(activity,EditAddAddress::class.java))
            finish()
        }

        notificationsprofile?.setOnClickListener{
            Toast. makeText(applicationContext," Currently In process ", Toast. LENGTH_SHORT).show()
           /* startActivity(Intent(activity,MyCart::class.java))
            finish()*/
        }

        helpprofile?.setOnClickListener{
            startActivity(Intent(activity,FaqActivity::class.java))
            finish()
        }

        logoutprofile?.setOnClickListener{
            startActivity(Intent(activity,LoginActivity::class.java))
            finish()
        }

        backarrow1?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }

    }


}