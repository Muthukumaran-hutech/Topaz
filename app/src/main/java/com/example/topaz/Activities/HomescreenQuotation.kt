package com.example.topaz.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.Adapters.SizeRecyclerAdapter
import com.example.topaz.R

class HomescreenQuotation : AppCompatActivity() {

    private lateinit var thicknessRecyclerView: RecyclerView
    private lateinit var thicknessAdapter: SizeRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen_quotation)



        thicknessRecyclerView = findViewById(R.id.thickrecycler)
        thicknessAdapter = SizeRecyclerAdapter()

        thicknessRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        thicknessRecyclerView.adapter = thicknessAdapter
    }
}