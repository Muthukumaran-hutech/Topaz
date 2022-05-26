package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.topaz.Interface.URIPathHelper
import com.example.topaz.R
import com.example.topaz.databinding.ActivityEditProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    val REQUEST_CODE = 100
    lateinit var activity: Activity
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        var custName = sharedPreference.getString("customerName", "")
        var custEmailId = sharedPreference.getString("email", "")
        var custPhoneno = sharedPreference.getString("primaryPhonenumber", "")



        binding.emailIdChange.setText(custEmailId)
        binding.phoneNoChange.setText(custPhoneno)
        binding.firstName.setText(custName)


        binding.emailIdChange.isEnabled = false
        binding.phoneNoChange.isEnabled = false



        binding.editImage.setOnClickListener {
            openGalleryForImage()
        }

        binding.change1.setOnClickListener {
            startActivity(Intent(activity, ChangeOldEmail::class.java))
            finish()
        }

        binding.change2.setOnClickListener {
            startActivity(Intent(activity, ChangeOldPhoneNumber::class.java))
            finish()
        }

        binding.resetBtn.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }



        binding.backarrow1.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))
            finish()
        }
    }

    private fun openGalleryForImage() {

        database = FirebaseDatabase.getInstance().getReference("Profile")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"


        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding.editProfileImage.setImageURI(data?.data)
            val uriPathHelper = URIPathHelper()
         //   val filePath = uriPathHelper.getPath(this,)// handle chosen image
        }
    }
}