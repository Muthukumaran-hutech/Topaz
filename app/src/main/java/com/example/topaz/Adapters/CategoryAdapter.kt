package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Activities.CategoryActivity
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.R

class CategoryAdapter(var list2: ArrayList<CategoriesModel>, var categoryPageItemClickListner1: CategoryPageItemClickListner) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list2[position]
        holder.bindItems(list2,position,categoryPageItemClickListner1)

    }

    override fun getItemCount(): Int {
        return list2.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage=itemView.findViewById<ImageView>(R.id.plwwoodss)
        var catName=itemView.findViewById<TextView>(R.id.plwwoodsstext)

        fun bindItems(listModel2:List<CategoriesModel>, position: Int, categoryPageItemClickListner1: CategoryPageItemClickListner) {
            catImage.setImageResource(listModel2[position].CateegoryImage)
            catName.text = listModel2[position].CateegoryName

            catImage.setOnClickListener {
                //Onclick will trigger the interface in activity
                categoryPageItemClickListner1.CategoryPageItemClickListner(listModel2[position])
            }
        }



    }
}