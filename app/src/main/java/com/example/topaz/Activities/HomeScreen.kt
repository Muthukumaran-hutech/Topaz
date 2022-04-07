package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.R

class HomeScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeCategoriesAdapter
    private var searchBtn: ImageView? = null
    private var notificationBtn: ImageView? = null
    private var myCartBtn: ImageView? = null
    private var homeBtn: ImageView? = null
    private var categoryBtn: ImageView? = null
    private var faviuriteBtn: ImageView? = null
    private var myAccountBtn: ImageView? = null
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        searchBtn = findViewById<ImageView>(R.id.search1)
        notificationBtn = findViewById<ImageView>(R.id.notification1)
        myCartBtn = findViewById<ImageView>(R.id.cart1)
        homeBtn = findViewById<ImageView>(R.id.home)
        categoryBtn = findViewById<ImageView>(R.id.categories)
        faviuriteBtn = findViewById<ImageView>(R.id.fav)
        myAccountBtn = findViewById<ImageView>(R.id.account)
        recyclerView = findViewById(R.id.categories_recyclerview)
        adapter = HomeCategoriesAdapter()
        activity = this


        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = adapter

        searchBtn?.setOnClickListener{
            startActivity(Intent(activity,SearchActivity::class.java))
            finish()
        }

        myCartBtn?.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))
            finish()
        }

        homeBtn?.setOnClickListener{
            Toast. makeText(applicationContext,"You Are Currently In Home Page",Toast. LENGTH_SHORT).show()
        }

        categoryBtn?.setOnClickListener{
            startActivity(Intent(activity,CategoryActivity::class.java))
            finish()
        }
    }
}