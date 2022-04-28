package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this


        binding.searchBackHome.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }
}