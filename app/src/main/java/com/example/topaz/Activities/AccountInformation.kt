package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivityLoginBinding
import com.google.gson.JsonObject
import org.json.JSONObject
import kotlin.math.log

class AccountInformation : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInformationBinding
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        binding.accountInfoUpdateBtn.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }

        binding.accountInfoSkipBtn.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }

        binding.accoutInfoBackkArrow.setOnClickListener{
            startActivity(Intent(activity,LoginActivity::class.java))
            finish()
        }
        customerDetails()
    }

    private fun customerDetails() {
        try {
            val json=  JsonObject()
            json.addProperty("customercode",1)
            json.addProperty("customerID","CS001")
            json.addProperty("customerName","Vishu")
            json.addProperty("primaryPhonenumber",7307294409)
            json.addProperty("secondaryPhonenumber","null")
            json.addProperty("email","gargvishu3011@gmail.com")
            json.addProperty("addressLine","NA")
            json.addProperty("city","Banglore")
            json.addProperty("state","Karnatka")
            json.addProperty("country","india")
            json.addProperty("zipcode",147001)


            val accountdetails = JsonObject()
            accountdetails.addProperty("accountid",1)
            accountdetails.addProperty("accountNumber","HDFC33093")
            accountdetails.addProperty("ifscCode","HDFC1111")
            accountdetails.addProperty("branchName","hdfc")
            accountdetails.addProperty("upiNumber","ABCD111")

            json.addProperty("accountdetails",accountdetails.toString())

            Log.d(ContentValues.TAG, "oncheckJson"+json)
        } catch (e: Exception) {
        }
    }
}