package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.R

class MyCart : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: MyCartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)


        cartRecyclerView = findViewById(R.id.cartrecycle)
        cartAdapter = MyCartAdapter()

        cartRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        cartRecyclerView.adapter = cartAdapter
    }
}