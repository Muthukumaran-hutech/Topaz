package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.Models.SubCatListModels
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding.inflate
import com.example.topaz.databinding.ActivityHomeScreenBinding

//lateinit var list: ArrayList<HomeCategoryModel>
//lateinit var homeScreenItemClickListner: HomeScreenItemClickListner
class HomeCategoriesAdapter(var catSubModels: ArrayList<SubCatListModels>, var homeScreenItemClickListner1: HomeScreenItemClickListner) : RecyclerView.Adapter<HomeCategoriesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_categories_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = catSubModels[position]
       // holder.imgtitle.text=data.HomeCategoryTitle
        holder.bindItems(catSubModels,position,homeScreenItemClickListner1)
    }

    override fun getItemCount(): Int {
        return catSubModels.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     var imgtitle=itemView.findViewById<TextView>(R.id.categoryTitle)

        fun bindItems(catSubModels:List<SubCatListModels>,position:Int,homeScreenItemClickListner1: HomeScreenItemClickListner){

            imgtitle.text=catSubModels.get(position).catTitle
            imgtitle.setOnClickListener {
                //Onclick will trigger the interface in activity
                homeScreenItemClickListner1.HomeScreenItemClickListner(catSubModels.get(position))
            }
        }
    }
}