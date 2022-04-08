package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.R

class MyCart : AppCompatActivity() {

    private var searchBtn: ImageView? = null
    private var notificationBtn: ImageView? = null
    private var myCartBtn: ImageView? = null
    private var backarrow: ImageView? = null
    lateinit var activity: Activity

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: MyCartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)

        searchBtn = findViewById<ImageView>(R.id.search)
        notificationBtn = findViewById<ImageView>(R.id.notification)
        myCartBtn = findViewById<ImageView>(R.id.cart)
        backarrow = findViewById<ImageView>(R.id.backarrow)
        activity = this



        cartRecyclerView = findViewById(R.id.cartrecycle)
        cartAdapter = MyCartAdapter()

        cartRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        cartRecyclerView.adapter = cartAdapter



        searchBtn?.setOnClickListener{
            startActivity(Intent(activity,SearchActivity::class.java))
            finish()
        }

        myCartBtn?.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))
            finish()
        }

        notificationBtn?.setOnClickListener{
            Toast. makeText(applicationContext," Currently In Process", Toast. LENGTH_SHORT).show()

        }

        backarrow?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }
}