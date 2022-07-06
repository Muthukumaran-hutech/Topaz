package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.R
import java.util.*
import kotlin.collections.ArrayList


class CategoryAdapter(var list2: ArrayList<CategoriesModel>, var categoryPageItemClickListner1: CategoryPageItemClickListner,var context:Context) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    var selectedposition=-1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list2[position]
        holder.bindItems(list2,position,categoryPageItemClickListner1,context)
        holder.itemView.setOnClickListener {
            //Onclick will trigger the interface in activity
            selectedposition = position
            categoryPageItemClickListner1.CategoryPageItemClickListner(list2[position])

        }
    }

    override fun getItemCount(): Int {
        return list2.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var catImage=itemView.findViewById<ImageView>(R.id.plwwoodss)
        var catName=itemView.findViewById<TextView>(R.id.plwwoodsstext)

        fun bindItems(
            listModel2: List<CategoriesModel>,
            position: Int,
            categoryPageItemClickListner1: CategoryPageItemClickListner,
            context: Context
        ) {

            var selectedposition=-1

            try {
                ///catImage.setImageResource(listModel2[position].CateegoryImage)
                catName.text = listModel2[position].CateegoryName

                //----------Write the logic----------------

                val decodedString: ByteArray =
                    Base64.decode(listModel2.get(position).CateegoryImage, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                /* if (listModel2.get(position).CateegoryImage.isEmpty()){*/


                Glide.with(context)
                    .applyDefaultRequestOptions(
                        RequestOptions().placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_image_24)
                    )
                    .load(bitmap)
                    .into(catImage)
                //catImage.setImageResource(R.drawable.ic_baseline_image_24)
                /*}*/




            }
            catch (e:Exception){
                e.toString()
            }
        }



    }

    fun resetAdapterList(){
        try{
            list2.clear()
            notifyDataSetChanged()
        }
        catch (e:Exception){
            e.toString()
        }
    }


}