package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.Interface.InnerCategoryItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.InnerCategoryModelList
import com.example.topaz.R
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityInnerCategoriesBinding

class InnerCategories : AppCompatActivity(), InnerCategoryItemClickListner {

    private lateinit var binding: ActivityInnerCategoriesBinding
    var inerCategorylist= java.util.ArrayList<InnerCategoryModelList>()
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInnerCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        binding.innerrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        var innercategoryAdapter= InnerCategoryAdapter(inerCategorylist,this)
        binding.innerrecycler.adapter = innercategoryAdapter
        binding.innerrecycler.setHasFixedSize(true)


        inerCategorylist.add(InnerCategoryModelList(R.drawable.plywoodbiards,"Wooden Waterproof Plywood Sheet, Thickness: 18 mm\n","₹59/ ","Square Feet","₹ 84","24% Off"))
        inerCategorylist.add(InnerCategoryModelList(R.drawable.pvcboards,"Waterproof Brown Plywood, Thickness: 4 - 25 mm","₹59/ ","Square Feet","₹ 84","24% Off"))
        inerCategorylist.add(InnerCategoryModelList(R.drawable.face_veneers,"Gurjan Waterproof Gurjan Plywood, Thickness: 15mm,","₹59/ ","Square Feet","₹ 84","24% Off"))


    }



    override fun InnerCategoryItemClickListner(Innercategories: InnerCategoryModelList) {
        startActivity(Intent(activity, ProductDetails::class.java))
        finish()
    }


}
