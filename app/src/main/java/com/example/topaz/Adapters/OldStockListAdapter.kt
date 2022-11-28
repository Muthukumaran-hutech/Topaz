package com.example.topaz.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topaz.ApiModels.ArrivalsPageItemClickListner
import com.example.topaz.Models.ArrivalsModels
import com.example.topaz.R
import com.example.topaz.Utility.Util

class OldStockListAdapter (var arrival_list: ArrayList<ArrivalsModels>, var arrivalsPageItemClickListner: ArrivalsPageItemClickListner, var context: Context) : RecyclerView.Adapter<OldStockListAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OldStockListAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_oldstock_adapter_layout, parent, false)
        return OldStockListAdapter.MyViewHolder(view)

    }


    override fun getItemCount(): Int {
        return arrival_list.size

    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var arrivalImage=itemView.findViewById<ImageView>(R.id.oldstock_plywood_image)
        var arrivalName=itemView.findViewById<TextView>(R.id.arrival_plwwoodsstext)
        var arrivalProductPrice = itemView.findViewById<TextView>(R.id.arrivalProductPrice)
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
                .applyDefaultRequestOptions(
                    RequestOptions().placeholder(R.drawable.plywoodbiards).error(
                        R.drawable.plywoodbiards))
                .load(bitmap)
                .into(arrivalImage)


            arrivalImage.setOnClickListener {
                //Onclick will trigger the interface in activity
                arrivalsPageItemClickListner.ArrivalsPageItemClickListner(arrivalList[position])
            }
            val sqfeettext="<font color='#0000'>"+"/Square Feet"+"</font>"

            if(arrivalList[position].Discount!=""){

                var discountvalue= Util.extractNumbersFromString(arrivalList[position].Discount,"Percentage")
                var discountamount = (arrivalList[position].SqfeetPrice!!.toInt()*discountvalue[0].toInt())/100
                var actualsqfeetprice = arrivalList[position].SqfeetPrice!!.toInt() - discountamount
                arrivalProductPrice.text= HtmlCompat.fromHtml(context.getString(R.string.Rs)+actualsqfeetprice.toString()+" "+sqfeettext,
                    HtmlCompat.FROM_HTML_MODE_LEGACY)


            }
            else{

                arrivalProductPrice.text= HtmlCompat.fromHtml(context.getString(R.string.Rs)+arrivalList[position].SqfeetPrice.toString()+" "+sqfeettext,
                    HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = arrival_list[position]
        holder.bindItems(arrival_list,position,arrivalsPageItemClickListner,context)
    }


}