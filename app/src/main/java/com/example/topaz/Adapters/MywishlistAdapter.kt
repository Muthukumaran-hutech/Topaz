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
import com.example.topaz.Interface.WishListItemClickListner
import com.example.topaz.Models.DetailsFirebaseModel
import com.example.topaz.R
import java.util.ArrayList

class
MywishlistAdapter(
    var wishData: ArrayList<DetailsFirebaseModel>,
    var wishListItemClickListner: WishListItemClickListner,
    var context: Context) : RecyclerView.Adapter<MywishlistAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_my_wishlist_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = wishData[position]
        holder.bindItems(wishData,position,wishListItemClickListner,context)
    }

    override fun getItemCount(): Int {
        return wishData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wishImage=itemView.findViewById<ImageView>(R.id.wishimage)
        var wishTitle=itemView.findViewById<TextView>(R.id.textView13)
        var wishRupees=itemView.findViewById<TextView>(R.id.textView14)
        var offer= itemView.findViewById<TextView>(R.id.textView17)
        var actualprice = itemView.findViewById<TextView>(R.id.textView15)
        var fav=itemView.findViewById<ImageView>(R.id.heart)



        fun bindItems(
            wishData: ArrayList<DetailsFirebaseModel>,
            position: Int,
            wishListItemClickListner: WishListItemClickListner,
            context: Context
        ) {
            wishTitle.text = wishData[position].productTitle
            wishRupees.text = context.getString(R.string.Rs)+wishData[position].price.toInt().toString()+"/"

            if(wishData[position].productDiscountId!=""){
                actualprice.visibility= View.VISIBLE
                actualprice.visibility= View.VISIBLE
               actualprice.text=wishData[position].actualPrice.toString()
                offer.text=wishData[position].productDiscountId
            }
            else{
                actualprice.visibility= View.GONE
                actualprice.visibility= View.GONE
            }

            val decodedString: ByteArray = Base64.decode(wishData.get(position).productImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
                .load(bitmap)
                .into(wishImage)

            fav.setOnClickListener {
                wishListItemClickListner.WishListItemClickListner(wishData[position],position)
            }

        }

    }
}