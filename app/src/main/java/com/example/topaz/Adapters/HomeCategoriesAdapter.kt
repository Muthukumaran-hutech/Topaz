package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding.inflate
import com.example.topaz.databinding.ActivityHomeScreenBinding

lateinit var list: ArrayList<HomeCategoryModel>
lateinit var homeScreenItemClickListner: HomeScreenItemClickListner
class HomeCategoriesAdapter(var list1: ArrayList<HomeCategoryModel>,var homeScreenItemClickListner1: HomeScreenItemClickListner) : RecyclerView.Adapter<HomeCategoriesAdapter.MyViewHolder>() {

    init {
        list=list1
        homeScreenItemClickListner1= homeScreenItemClickListner

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_categories_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        holder.bindItems(list,position)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     var imgtitle=itemView.findViewById<TextView>(R.id.plywood)

        fun bindItems(listModel:List<HomeCategoryModel>,position:Int){

            imgtitle.setOnClickListener {
                homeScreenItemClickListner.HomeScreenItemClickListner(listModel.get(position))
            }
        }
    }
}