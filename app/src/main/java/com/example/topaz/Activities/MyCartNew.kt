package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartNewAdapter
import com.example.topaz.Adapters.OrderSummaryAdapter
import com.example.topaz.R

class MyCartNew : AppCompatActivity() {

    private lateinit var mycart2RecyclerView: RecyclerView
    private lateinit var mycart2Adapter: MyCartNewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart_new)


        mycart2RecyclerView = findViewById(R.id.myrecycler)
        mycart2Adapter = MyCartNewAdapter()

        mycart2RecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        mycart2RecyclerView.adapter = mycart2Adapter
    }
}