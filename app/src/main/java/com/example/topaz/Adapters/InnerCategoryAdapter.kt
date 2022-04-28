package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Interface.InnerCategoryItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.InnerCategoryModelList
import com.example.topaz.R

class InnerCategoryAdapter(var list3: ArrayList<InnerCategoryModelList>, var innerCategoryItemClickListner: InnerCategoryItemClickListner) : RecyclerView.Adapter<InnerCategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_inner_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list3[position]
        holder.bindItems(list3,position,innerCategoryItemClickListner)

    }

    override fun getItemCount(): Int {
        return list3.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var innerCatImage=itemView.findViewById<ImageView>(R.id.innercatimage)

        fun bindItems(listModel3:List<InnerCategoryModelList>, position: Int, innerCategoryItemClickListner1: InnerCategoryItemClickListner) {
            innerCatImage.setImageResource(listModel3.get(position).InnerCateegoryImage)

            innerCatImage.setOnClickListener {
                //Onclick will trigger the interface in activity
                innerCategoryItemClickListner1.InnerCategoryItemClickListner(listModel3.get(position))
            }
        }

    }
}