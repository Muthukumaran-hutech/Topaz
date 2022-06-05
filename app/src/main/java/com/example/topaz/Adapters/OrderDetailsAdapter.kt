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
import com.example.topaz.Activities.OrderDetails
import com.example.topaz.Interface.OrdDetPageItemclickListner
import com.example.topaz.Models.OrderModels
import com.example.topaz.R
import java.util.ArrayList

class OrderDetailsAdapter(
   var orderListItem2: ArrayList<OrderModels>,
   var ordDetPageItemclickListner: OrdDetPageItemclickListner,
   var  context: Context
) : RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_order_details_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = orderListItem2[position]
        holder.bindItems(orderListItem2,position,ordDetPageItemclickListner,context)

    }

    override fun getItemCount(): Int {
        return orderListItem2.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img=itemView.findViewById<ImageView>(R.id.or_image)
        var title=itemView.findViewById<TextView>(R.id.title)
        var price=itemView.findViewById<TextView>(R.id.pri)
        fun bindItems(
            orderListItem: ArrayList<OrderModels>,
            position: Int,
            ordDetPageItemclickListner: OrdDetPageItemclickListner,
            context: Context
        ) {
            title.text = orderListItem[position].quotationTitle
            price.text = orderListItem[position].quotationSqftPrice.toString()


            val decodedString: ByteArray = Base64.decode(orderListItem.get(position).image,
                Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            /* if (listModel2.get(position).CateegoryImage.isEmpty()){*/


            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
                .load(bitmap)
                .into(img)
        }

    }
}