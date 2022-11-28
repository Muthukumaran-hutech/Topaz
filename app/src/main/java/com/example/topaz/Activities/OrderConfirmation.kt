package com.example.topaz.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.topaz.R
import com.example.topaz.databinding.ActivityOrderConfirmationBinding

class OrderConfirmation : AppCompatActivity() {
    private lateinit var binding:ActivityOrderConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityOrderConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val type=intent.getStringExtra("type")
        if(type.equals("OrderPlacement")){
            binding.txtreuest.text= "Your order has been placed successfully"
            binding.companynm.visibility=View.GONE
            binding.torequest.visibility=View.GONE
            binding.goHomeButton.visibility = View.VISIBLE
        }

        binding.goHomeButton.setOnClickListener{
            startActivity(Intent(this,HomeScreen::class.java))
            finish()
        }


    }
}