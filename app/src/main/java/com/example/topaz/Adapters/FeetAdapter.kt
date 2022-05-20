package com.example.topaz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Models.FeetModel
import com.example.topaz.R

class FeetAdapter(var feet_list: ArrayList<FeetModel>): RecyclerView.Adapter<FeetAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeetAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_feet_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeetAdapter.MyViewHolder, position: Int) {
        val data = feet_list[position]
        holder.feet.setText(data.Product_Feet.toString())
    }

    override fun getItemCount(): Int {
       return feet_list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var feet=itemView.findViewById<TextView>(R.id.fee_t)


    }
}