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
import com.example.topaz.Activities.MyCart
import com.example.topaz.Interface.MyCartItemClickListner
import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.example.topaz.R
import java.util.ArrayList

class MyCartAdapter(var cartData: ArrayList<CartProductList>, var myCartItemClickListner: MyCartItemClickListner,var context: Context) : RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_my_cart_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = cartData[position]
        holder.bindItems(cartData,position,myCartItemClickListner,context)
    }

    override fun getItemCount(): Int {
        return cartData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cartImage=itemView.findViewById<ImageView>(R.id.catimage)
        var cartTitle=itemView.findViewById<TextView>(R.id.textView13)
        var cartRupees=itemView.findViewById<TextView>(R.id.textView14).toString()
        var cartDelete=itemView.findViewById<ImageView>(R.id.textView15)

        fun bindItems(
            cartData: ArrayList<CartProductList>,
            position: Int,
            myCartItemClickListner: MyCartItemClickListner,
            context: Context
        ) {
            cartTitle.text = cartData[position].product_title
            cartRupees = cartData[position].price.toInt().toString()
            val decodedString: ByteArray = Base64.decode(cartData.get(position).cartImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
                .load(bitmap)
                .into(cartImage)
            cartDelete.setOnClickListener {
                myCartItemClickListner.MyCartItemClickListner(cartData[position])
            }

        }

    }
}