package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.OrderSummaryAdapter
import com.example.topaz.R

class OrderSummary : AppCompatActivity() {


    private lateinit var ordersummaryRecyclerView: RecyclerView
    private lateinit var ordersummaryAdapter: OrderSummaryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        ordersummaryRecyclerView = findViewById(R.id.ordersummaryrecycler)
        ordersummaryAdapter = OrderSummaryAdapter()

        ordersummaryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        ordersummaryRecyclerView.adapter = ordersummaryAdapter
    }
}