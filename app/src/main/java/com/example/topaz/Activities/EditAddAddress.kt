package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.topaz.R
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityEditAddAddressBinding

class EditAddAddress : AppCompatActivity() {

    private lateinit var binding: ActivityEditAddAddressBinding

    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        setSupportActionBar(binding.editAddressToolbar)
        supportActionBar?.title = ""

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custName = sharedPreference.getString("customerName", "")
        var custId = sharedPreference.getString("customercode", "")
        var custAddress = sharedPreference.getString("addressLine", "")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber", "")
        //var custEmailId = sharedPreference.getString("email", "")
        var custCity = sharedPreference.getString("city", "")
        var custState = sharedPreference.getString("state", "")

        binding.nameAddress.setText(custName)
        binding.phoneNoChange.setText(custPhoneno)
        binding.locality.setText(custAddress)
        binding.houseNo.setText(custAddress)
        binding.city.setText(custCity)
        binding.state.setText(custState)


        binding.categoryBackArrow.setOnClickListener {
           /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()*/
        }

        binding.saveAddressBtn?.setOnClickListener {
            updateuserinfo()
        }



    }

    private fun updateuserinfo() {

    }
}