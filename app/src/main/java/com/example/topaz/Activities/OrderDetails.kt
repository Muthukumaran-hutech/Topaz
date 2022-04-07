package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.OrderDetailsAdapter
import com.example.topaz.R

class OrderDetails : AppCompatActivity() {

    private lateinit var orderdetailsRecyclerView: RecyclerView
    private lateinit var orderdetailsAdapter: OrderDetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        orderdetailsRecyclerView = findViewById(R.id.detailsrecycler)
        orderdetailsAdapter = OrderDetailsAdapter()

        orderdetailsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        orderdetailsRecyclerView.adapter = orderdetailsAdapter
    }
}