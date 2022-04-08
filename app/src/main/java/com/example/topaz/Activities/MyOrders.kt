package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.MyOrdersAdapter
import com.example.topaz.R

class MyOrders : AppCompatActivity() {


    private var backarrow: ImageView? = null
    private var myCartBtn: ImageView? = null
    private var searchBtn: ImageView? = null
    lateinit var activity: Activity

    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: MyOrdersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)


        backarrow = findViewById<ImageView>(R.id.backarrow)
        myCartBtn = findViewById<ImageView>(R.id.cart)
        searchBtn = findViewById<ImageView>(R.id.search)
        activity = this


        ordersRecyclerView = findViewById(R.id.ordersrecycler)
        ordersAdapter = MyOrdersAdapter()

        ordersRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ordersRecyclerView.adapter = ordersAdapter


        searchBtn?.setOnClickListener{
            startActivity(Intent(activity,SearchActivity::class.java))
            finish()
        }

        myCartBtn?.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))
            finish()
        }


        backarrow?.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))
            finish()
        }
    }
}