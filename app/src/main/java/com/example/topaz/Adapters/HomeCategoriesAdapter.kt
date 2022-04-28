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

//lateinit var list: ArrayList<HomeCategoryModel>
//lateinit var homeScreenItemClickListner: HomeScreenItemClickListner
class HomeCategoriesAdapter(var list1: ArrayList<HomeCategoryModel>,var homeScreenItemClickListner1: HomeScreenItemClickListner) : RecyclerView.Adapter<HomeCategoriesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_categories_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list1[position]
       // holder.imgtitle.text=data.HomeCategoryTitle
        holder.bindItems(list1,position,homeScreenItemClickListner1)
    }

    override fun getItemCount(): Int {
        return list1.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     var imgtitle=itemView.findViewById<TextView>(R.id.categoryTitle)

        fun bindItems(listModel:List<HomeCategoryModel>,position:Int,homeScreenItemClickListner1: HomeScreenItemClickListner){

            imgtitle.text=listModel.get(position).HomeCategoryTitle
            imgtitle.setOnClickListener {
                //Onclick will trigger the interface in activity
                homeScreenItemClickListner1.HomeScreenItemClickListner(listModel.get(position))
            }
        }
    }
}