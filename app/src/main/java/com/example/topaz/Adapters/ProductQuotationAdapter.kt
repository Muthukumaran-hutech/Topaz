package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Interface.ProductThicknessItemClickListner
import com.example.topaz.Models.ThicknessModel
import com.example.topaz.R

class ProductQuotationAdapter(var thickness_list: ArrayList<ThicknessModel>) : RecyclerView.Adapter<ProductQuotationAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_product_quotation_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = thickness_list[position]
        holder.thick.setText(data.Product_Thickness.toString())

    }

    override fun getItemCount(): Int {
        return thickness_list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thick=itemView.findViewById<TextView>(R.id.thick_ness)


    }
}