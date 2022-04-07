package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.R

class InnerCategories : AppCompatActivity() {

    private lateinit var innercategoryRecyclerView: RecyclerView
    private lateinit var innercategoryAdapter: InnerCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_categories)

        innercategoryRecyclerView = findViewById(R.id.innerrecycler)
        innercategoryAdapter = InnerCategoryAdapter()

        innercategoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        innercategoryRecyclerView.adapter = innercategoryAdapter
    }
}