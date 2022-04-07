package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.MyOrdersAdapter
import com.example.topaz.R

class MyOrders : AppCompatActivity() {

    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: MyOrdersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        ordersRecyclerView = findViewById(R.id.ordersrecycler)
        ordersAdapter = MyOrdersAdapter()

        ordersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        ordersRecyclerView.adapter = ordersAdapter
    }
}