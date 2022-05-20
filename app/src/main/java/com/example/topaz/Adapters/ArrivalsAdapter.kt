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
import com.example.topaz.Activities.HomeScreen
import com.example.topaz.ApiModels.ArrivalsPageItemClickListner
import com.example.topaz.Models.ArrivalsModels
import com.example.topaz.R

class ArrivalsAdapter(
    var arrival_list: ArrayList<ArrivalsModels>, var arrivalsPageItemClickListner: ArrivalsPageItemClickListner, var context: Context) : RecyclerView.Adapter<ArrivalsAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_arrivals_adapter, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val data = arrival_list[position]
        holder.bindItems(arrival_list,position,arrivalsPageItemClickListner,context)
    }

    override fun getItemCount(): Int {
        return arrival_list.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var arrivalImage=itemView.findViewById<ImageView>(R.id.arrival_plwwoodss)
        var arrivalName=itemView.findViewById<TextView>(R.id.arrival_plwwoodsstext)
        fun bindItems(
            arrivalList: List<ArrivalsModels>,
            position: Int,
            arrivalsPageItemClickListner: ArrivalsPageItemClickListner,
            context: Context
        ) {
            arrivalName.text = arrivalList[position].ArrivalName

            val decodedString: ByteArray = Base64.decode(arrivalList.get(position).ArrivalImage,
                Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
                .load(bitmap)
                .into(arrivalImage)


            arrivalImage.setOnClickListener {
                //Onclick will trigger the interface in activity
                arrivalsPageItemClickListner.ArrivalsPageItemClickListner(arrivalList[position])
            }
        }

    }
}