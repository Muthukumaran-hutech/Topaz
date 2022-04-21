package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.R

class CategoryActivity : AppCompatActivity() {

    private var searchBtn: ImageView? = null
    private var notificationBtn: ImageView? = null
    private var myCartBtn: ImageView? = null
    private var homeBtn: ImageView? = null
    private var categoryBtn: ImageView? = null
    private var faviuriteBtn: ImageView? = null
    private var myAccountBtn: ImageView? = null
    private var backarrow2: ImageView? = null
    lateinit var activity: Activity

    private lateinit var categorymainRecyclerView: RecyclerView
    private lateinit var categorymainAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        searchBtn = findViewById<ImageView>(R.id.search)
        notificationBtn = findViewById<ImageView>(R.id.notification)
        myCartBtn = findViewById<ImageView>(R.id.cart)
        homeBtn = findViewById<ImageView>(R.id.home)
        categoryBtn = findViewById<ImageView>(R.id.categories)
        faviuriteBtn = findViewById<ImageView>(R.id.fav)
        myAccountBtn = findViewById<ImageView>(R.id.account)
        backarrow2 = findViewById<ImageView>(R.id.backarrow2)
        activity = this

        categorymainRecyclerView = findViewById(R.id.catview)
        categorymainAdapter = CategoryAdapter()

        categorymainRecyclerView.layoutManager = GridLayoutManager(this, 3)//Count depicts no of elements in row
        categorymainRecyclerView.adapter = categorymainAdapter


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

        homeBtn?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()

        }

        categoryBtn?.setOnClickListener{
            Toast. makeText(applicationContext,"You Are Currently In Category Page", Toast. LENGTH_SHORT).show()
        }

        myAccountBtn?.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }

        faviuriteBtn?.setOnClickListener{
            startActivity(Intent(activity,MyWishlist::class.java))
            finish()
        }

        backarrow2?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }

}