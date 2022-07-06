package com.example.topaz.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Models.SubCatListModels
import com.example.topaz.R

//lateinit var list: ArrayList<HomeCategoryModel>
//lateinit var homeScreenItemClickListner: HomeScreenItemClickListner
class HomeCategoriesAdapter(var catSubModels: ArrayList<SubCatListModels>, var homeScreenItemClickListner1: HomeScreenItemClickListner) : RecyclerView.Adapter<HomeCategoriesAdapter.MyViewHolder>() {

  lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_categories_adapter, parent, false)
        context=view.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = catSubModels[position]
       // holder.imgtitle.text=data.HomeCategoryTitle
        holder.bindItems(catSubModels,position,homeScreenItemClickListner1,context)
        holder.itemView.setOnClickListener {
            if(position>=0) {
                homeScreenItemClickListner1.HomeScreenItemClickListner(
                    catSubModels.get(position),
                    position
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return catSubModels.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     var imgtitle=itemView.findViewById<TextView>(R.id.categoryTitle)
     var categorylayout = itemView.findViewById<LinearLayout>(R.id.category_layout)
     var categoryIcon = itemView.findViewById<ImageView>(R.id.category_image)

        fun bindItems(
            catSubModels: List<SubCatListModels>,
            position: Int,
            homeScreenItemClickListner1: HomeScreenItemClickListner,
            context: Context
        ){

            imgtitle.text=catSubModels.get(position).catTitle

            if(catSubModels.get(position).isCategoryClicked){
               categorylayout.background=AppCompatResources.getDrawable(context,R.drawable.roundedbtnlite)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imgtitle.setTextColor(context.getColor(R.color.white))
                    categoryIcon.setColorFilter(context.getColor(R.color.white))
                }
            }
            else{
                categorylayout.background=AppCompatResources.getDrawable(context,R.drawable.category_btn_unselected)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imgtitle.setTextColor(context.getColor(R.color.black))
                    categoryIcon.setColorFilter(context.getColor(R.color.blue))
                }
            }
           /* itemView.setOnClickListener {
                //Onclick will trigger the interface in activity
                homeScreenItemClickListner1.HomeScreenItemClickListner(catSubModels.get(position),position)
            }*/
        }
    }
    public fun changeClickedState(position: Int,status:Boolean){
        catSubModels.get(position).isCategoryClicked=status
        notifyDataSetChanged()


    }

    public fun resetCategorySelection(){
        try{
            catSubModels.clear()
            notifyDataSetChanged()


        }
        catch (e:Exception){
            e.toString()
        }
    }

}