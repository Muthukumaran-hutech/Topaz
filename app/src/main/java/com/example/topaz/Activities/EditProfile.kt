package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.topaz.R
import com.example.topaz.databinding.ActivityEditProfileBinding
import com.example.topaz.databinding.ActivityLoginBinding

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private var backarrow1: ImageView? = null
    private var profile_change: ImageView? = null
    private var change_Email: TextView? = null
    private var change_Phoneno: TextView? = null
    private var change_Pwd: TextView? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        val sharedPreference =  getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custName = sharedPreference.getString("customerName","")
        var custEmailId = sharedPreference.getString("email","")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber","")

        profile_change = findViewById<ImageView>(R.id.edit_image)

        backarrow1 = findViewById<ImageView>(R.id.backarrow1)

        binding.emailIdChange.setText(custEmailId)
        binding.phoneNoChange.setText(custPhoneno)
        binding.firstName.setText(custName)


        binding.emailIdChange.isEnabled = false
        binding.phoneNoChange.isEnabled = false

        profile_change?.setOnClickListener{
            Toast. makeText(applicationContext," Currently In process ", Toast. LENGTH_SHORT).show()

        }


        binding.change1.setOnClickListener{
            startActivity(Intent(activity,ChangeOldEmail::class.java))
            finish()
        }

        binding.change2.setOnClickListener{
            startActivity(Intent(activity,ChangeOldPhoneNumber::class.java))
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