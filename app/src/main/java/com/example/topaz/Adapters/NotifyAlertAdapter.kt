package com.example.topaz.Adapters

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
import com.example.topaz.Fragments.AlertFragment
import com.example.topaz.Interface.NotifyAlertItemClickListner
import com.example.topaz.Models.NotifyAlertModel
import com.example.topaz.R
import java.util.ArrayList

class NotifyAlertAdapter(
    var offerAlertlist: ArrayList<NotifyAlertModel>,
    var notificationAlertItemClickListner: NotifyAlertItemClickListner, var context: AlertFragment
) : RecyclerView.Adapter<NotifyAlertAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_notification_alert_adapter, parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = offerAlertlist[position]
        holder.bindItems(offerAlertlist, position, notificationAlertItemClickListner,context)
    }


    override fun getItemCount(): Int {
       return offerAlertlist.size
    }

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    var alertimage = itemView.findViewById<ImageView>(R.id.alertimage)
    var alerttext = itemView.findViewById<TextView>(R.id.alerttext)
    var alertprice = itemView.findViewById<TextView>(R.id.alertprice)
    var dt_date = itemView.findViewById<TextView>(R.id.dt_date)

    fun bindItems(
        offerAlertlist: ArrayList<NotifyAlertModel>,
        position: Int,
        notificationAlertItemClickListner: NotifyAlertItemClickListner, context: AlertFragment
    ) {

        alerttext.text = offerAlertlist[position].OfferTitle
        alertprice.text = offerAlertlist[position].OfferPrice
        dt_date.text = offerAlertlist[position].OffercreatedDate


        val decodedString: ByteArray = Base64.decode(offerAlertlist.get(position).OfferImage,Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)



        Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
            .load(bitmap)
            .into(alertimage)

        alertimage.setOnClickListener {
            //Onclick will trigger the interface in activity
            notificationAlertItemClickListner.NotifyAlertItemClickListner(offerAlertlist[position])
        }

    }

}

}