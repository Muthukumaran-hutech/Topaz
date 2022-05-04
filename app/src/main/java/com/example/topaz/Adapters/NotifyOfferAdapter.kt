package com.example.topaz.Adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Interface.NotifyOfferItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.NotifyOfferModels
import com.example.topaz.R

class NotifyOfferAdapter(var offerList: ArrayList<NotifyOfferModels>, var notifyOfferItemClickListner: NotifyOfferItemClickListner) : RecyclerView.Adapter<NotifyOfferAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notify_offer_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = offerList[position]
        holder.bindItems(offerList, position, notifyOfferItemClickListner)
    }

    override fun getItemCount(): Int {
        return offerList.count()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var offerImage = itemView.findViewById<ImageView>(R.id.offer_image)
        var offerTitle = itemView.findViewById<TextView>(R.id.offer_title)
        var offerDesc = itemView.findViewById<TextView>(R.id.offer_desc)

        fun bindItems(
            offerList: List<NotifyOfferModels>,
            position: Int,
            notifyOfferItemClickListner: NotifyOfferItemClickListner
        ) {
            ///catImage.setImageResource(listModel2[position].CateegoryImage)
            offerTitle.text = offerList[position].OfferTitle
            offerDesc.text = offerList[position].OfferDesc

            //----------Write the logic----------------

            val decodedString: ByteArray = Base64.decode(
                offerList.get(position).OfferImage,
                Base64.DEFAULT
            )
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            offerImage.setImageBitmap(bitmap)

            offerImage.setOnClickListener {
                //Onclick will trigger the interface in activity
                notifyOfferItemClickListner.NotifyOfferItemClickListner(offerList[position])
            }

        }

    }
}