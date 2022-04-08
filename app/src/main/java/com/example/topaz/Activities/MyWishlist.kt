package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.R

class MyWishlist : AppCompatActivity() {

    private var myCartBtn: ImageView? = null
    /*private var homeBtn: ImageView? = null
    private var categoryBtn: ImageView? = null
    private var faviuriteBtn: ImageView? = null
    private var myAccountBtn: ImageView? = null*/
    private var backarrow2: ImageView? = null
    lateinit var activity: Activity

    private lateinit var wishlistRecyclerView: RecyclerView
    private lateinit var wishlistAdapter: MywishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wishlist)


        myCartBtn = findViewById<ImageView>(R.id.cart)
        /*homeBtn = findViewById<ImageView>(R.id.home)
        categoryBtn = findViewById<ImageView>(R.id.categories)
        faviuriteBtn = findViewById<ImageView>(R.id.fav)
        myAccountBtn = findViewById<ImageView>(R.id.account)*/
        backarrow2 = findViewById<ImageView>(R.id.backarrow2)
        activity = this


        wishlistRecyclerView = findViewById(R.id.wishlistadapter)
        wishlistAdapter = MywishlistAdapter()

        wishlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        wishlistRecyclerView.adapter = wishlistAdapter





        myCartBtn?.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))
            finish()
        }



       /* homeBtn?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()

        }

        categoryBtn?.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()

        }

        myAccountBtn?.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }

        faviuriteBtn?.setOnClickListener{
            Toast. makeText(applicationContext,"You Are Currently In Favourites", Toast. LENGTH_SHORT).show()
        }
*/
        backarrow2?.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }
}