package com.example.topaz.Activities

import android.app.Activity
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
        activity = this



        binding.categoryBackArrow.setOnClickListener{
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
    }
}