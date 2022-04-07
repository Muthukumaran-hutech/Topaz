package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.Adapters.SizeRecyclerAdapter
import com.example.topaz.R

class ProductQuotation : AppCompatActivity() {

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductQuotationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_quotation)

        productRecyclerView = findViewById(R.id.productquotqtionrecycler)
        productAdapter = ProductQuotationAdapter()

        productRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        productRecyclerView.adapter = productAdapter
    }
}