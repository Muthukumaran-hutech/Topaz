package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
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
import com.example.topaz.Utility.Util
import java.util.regex.Pattern

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
        var discount=itemView.findViewById<TextView>(R.id.textView17)
        var initialprice=itemView.findViewById<TextView>(R.id.textView15)

        fun bindItems(listModel3:List<InnerCategoryModelList>, position: Int, innerCategoryItemClickListner1: InnerCategoryItemClickListner,context: Context) {
            //innerCatImage.setImageResource(listModel3.get(position).InnerCateegoryImage)
         try {
             catName.text = listModel3[position].InnerCateegoryTitle
             catthick.text = listModel3[position].InnerCateegoryhickness
             catprice.text =
                 context.getString(R.string.Rs) + listModel3[position].InnerCateegoryPrice + "/"
             if (listModel3[position].discount != null && listModel3[position].discount != "") {
                 discount.visibility=View.VISIBLE
                 initialprice.visibility=View.VISIBLE
                 discount.text = listModel3[position].discount
                 var discountvalue = Util.extractNumbersFromString(listModel3[position].discount!!,"Percentage")
                 var discountamount =
                     (listModel3[position].InnerCateegoryPrice.toInt() * discountvalue[0].toInt()) / 100
                 var actualsqfeetvalue =
                     listModel3[position].InnerCateegoryPrice.toInt() - discountamount
                 catprice.text = context.getString(R.string.Rs) + actualsqfeetvalue + "/"
                 initialprice.text =
                     context.getString(R.string.Rs) + listModel3[position].InnerCateegoryPrice
             }
             else{
                 discount.visibility=View.INVISIBLE
                 initialprice.visibility=View.INVISIBLE
             }
             var size = listModel3[position].size
             /* size?.let {val number=Regex("\\D+").split(it)
                Log.d("--Digits--",number[1].toString())

            }*/


             val decodedString: ByteArray = Base64.decode(
                 listModel3.get(position).InnerCateegoryImage,
                 Base64.DEFAULT
             )
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


             catImage.setOnClickListener {
                 //Onclick will trigger the interface in activity
                 innerCategoryItemClickListner1.InnerCategoryItemClickListner(listModel3[position])
             }

        }
         catch (e:Exception){
             e.printStackTrace()

         }         }

    }
}