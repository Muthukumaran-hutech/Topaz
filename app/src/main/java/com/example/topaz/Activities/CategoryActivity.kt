package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.R

class CategoryActivity : AppCompatActivity() {

    private lateinit var categorymainRecyclerView: RecyclerView
    private lateinit var categorymainAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categorymainRecyclerView = findViewById(R.id.catview)
        categorymainAdapter = CategoryAdapter()

        categorymainRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        categorymainRecyclerView.adapter = categorymainAdapter
    }

}