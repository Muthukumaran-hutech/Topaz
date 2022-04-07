package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.R

class MyWishlist : AppCompatActivity() {

    private lateinit var wishlistRecyclerView: RecyclerView
    private lateinit var wishlistAdapter: MywishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wishlist)


        wishlistRecyclerView = findViewById(R.id.wishlistadapter)
        wishlistAdapter = MywishlistAdapter()

        wishlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        wishlistRecyclerView.adapter = wishlistAdapter
    }
}