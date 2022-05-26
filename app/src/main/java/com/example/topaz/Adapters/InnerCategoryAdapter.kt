package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Interface.InnerCategoryItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.InnerCategoryModelList
import com.example.topaz.R

class InnerCategoryAdapter(var list3: ArrayList<InnerCategoryModelList>, var innerCategoryItemClickListner: InnerCategoryItemClickListner,var context: Context) : RecyclerView.Adapter<InnerCategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_inner_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list3[position]
        holder.bindItems(list3,position,innerCategoryItemClickListner,context)

    }

    override fun getItemCount(): Int {
        return list3.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage=itemView.findViewById<ImageView>(R.id.innercatimage)
        var catName=itemView.findViewById<TextView>(R.id.textView13)
        var catthick=itemView.findViewById<TextView>(R.id.textView44)
        var catprice=itemView.findViewById<TextView>(R.id.textView14)

        fun bindItems(listModel3:List<InnerCategoryModelList>, position: Int, innerCategoryItemClickListner1: InnerCategoryItemClickListner,context: Context) {
            //innerCatImage.setImageResource(listModel3.get(position).InnerCateegoryImage)

            catName.text = listModel3[position].InnerCateegoryTitle
            catthick.text = listModel3[position].InnerCateegoryhickness
            catprice.text = listModel3[position].InnerCateegoryPrice

            val decodedString: ByteArray = Base64.decode(listModel3.get(position).InnerCateegoryImage,
                Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            /* if (listModel2.get(position).CateegoryImage.isEmpty()){*/


            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
                .load(bitmap)
                .into(catImage)
            //catImage.setImageResource(R.drawable.ic_baseline_image_24)
            /*}*/


            catImage.setOnClickListener {
                //Onclick will trigger the interface in activity
                innerCategoryItemClickListner1.InnerCategoryItemClickListner(listModel3[position])
            }
        }

    }
}