package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.topaz.R
import com.example.topaz.databinding.ActivityMyAccountBinding

class MyAccount : AppCompatActivity() {

    private lateinit var binding: ActivityMyAccountBinding

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
        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this

        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        val editor=sharedPreference.edit()

        var custName = sharedPreference.getString("customerName","")
        var custEmailId = sharedPreference.getString("email","")

        Log.d("check1:", custName.toString())
        binding.profileName.setText(custName)
        binding.editeProfileMail.setText(custEmailId)
        binding.editEmailIcon.setOnClickListener {
            startActivity(Intent(activity,ChangeOldEmail::class.java))
            finish()
        }



        profile_image = findViewById<ImageView>(R.id.profile_image)
        editemailicon = findViewById<ImageView>(R.id.edit_email_icon)
        myorderprofile = findViewById<LinearLayout>(R.id.myorderprofile)
        myaddressprofile = findViewById<LinearLayout>(R.id.myaddressprofile)
        notificationsprofile = findViewById<LinearLayout>(R.id.notificationsprofile)
        helpprofile = findViewById<LinearLayout>(R.id.helpprofile)
        logoutprofile = findViewById<LinearLayout>(R.id.logout_profile)
        backarrow1 = findViewById<ImageView>(R.id.backarrow1)



       /* profile_image?.setOnClickListener{
            startActivity(Intent(activity,EditProfile::class.java))
            finish()
        }*/


        editemailicon?.setOnClickListener{
            startActivity(Intent(activity,EditProfile::class.java))
            finish()
        }

        myorderprofile?.setOnClickListener{
            startActivity(Intent(activity,MyOrders::class.java))
            finish()
        }

        myaddressprofile?.setOnClickListener{
            startActivity(Intent(activity,EditAddAddress::class.java))
            //finish()
        }

        notificationsprofile?.setOnClickListener{
            startActivity(Intent(activity,Notifications::class.java))
            finish()
        }

        helpprofile?.setOnClickListener{
            startActivity(Intent(activity,FaqActivity::class.java))
            finish()
        }

        binding.logoutProfile.setOnClickListener{

            var message = "Are you sure you want to Logout? "
            //message += "\n\n " + binding.phoneContainer.helperText
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(message)
                .setPositiveButton("Yes") { _, _ ->
                    editor.putString("isUserLoggedIn","false").apply()
                    startActivity(Intent(activity,LoginActivity::class.java))
                    finish()
                }.setNegativeButton("No") { _, _ ->
                    //doNothing
                }.show()

        }

        backarrow1?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }

    }


}