package com.example.topaz.Adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.topaz.Interface.IncreementDecreementItemClickListner
import com.example.topaz.Interface.MyCartItemClickListner
import com.example.topaz.Models.CartProductList
import com.example.topaz.R
import java.util.ArrayList

class MyCartAdapter(
    var cartData: ArrayList<CartProductList>,
    var myCartItemClickListner: MyCartItemClickListner,
    var context: Context,
    var increementDecreementItemClickListner: IncreementDecreementItemClickListner
) : RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {

    var quantity = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_my_cart_adapter, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = cartData[position]
        holder.bindItems(cartData, position, myCartItemClickListner, context)

        holder.number.isEnabled = false
        if (cartData.get(position).quantity.isEmpty()){
            quantity = 1
        }
        else{
            quantity=cartData.get(position).quantity.toInt()
            holder.number.setText(cartData.get(position).quantity.toString())
        }


        holder.plus.setOnClickListener {

            if (cartData.get(position).quantity.isNotEmpty()) {
                if (cartData.get(position).quantity.toInt() >= 1) {
                    //var qnty = cartData.get(position).quantity.toInt()
                    quantity++
                    holder.number.setText(quantity.toString())

                } else {
                    //doNothing
                }
            }else{
                quantity++
                holder.number.setText(quantity.toString())
            }
            increementDecreementItemClickListner.IncreementDecreementItemClickListner(cartData.get(position),quantity)
        }
        holder.minus.setOnClickListener {
            if (cartData.get(position).quantity.isNotEmpty()) {
                if (cartData.get(position).quantity.toInt() >= 1) {
                    //var qnt = cartData.get(position).quantity.toInt()
                    quantity--
                    if (quantity>0){
                        holder.number.setText(quantity.toString())
                    }else{
                        quantity=1
                    }


                } else {
                    //doNothing
                }
            }
            else{

                quantity--
                if (quantity>0){
                    holder.number.setText(quantity.toString())
                }else{
                    quantity=1
                }

            }
            increementDecreementItemClickListner.IncreementDecreementItemClickListner(cartData.get(position),quantity)


        }
    }

    override fun getItemCount(): Int {
        return cartData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cartImage = itemView.findViewById<ImageView>(R.id.catimage)
        var cartTitle = itemView.findViewById<TextView>(R.id.textView13)
        var cartRupees = itemView.findViewById<TextView>(R.id.pri)
        var cartDelete = itemView.findViewById<ImageView>(R.id.textView15)
        var minus = itemView.findViewById<Button>(R.id.minusbtn)
        var plus = itemView.findViewById<Button>(R.id.plusbtn)
        var number = itemView.findViewById<EditText>(R.id.numbercount)


        fun bindItems(
            cartData: ArrayList<CartProductList>,
            position: Int,
            myCartItemClickListner: MyCartItemClickListner,
            context: Context
        ) {
            try {
                cartTitle.text = cartData[position].product_title+"\n"+"Thickness:" +" "+cartData[position].thickness
                cartRupees.text = context.getString(R.string.Rs) + cartData[position].price + "/"
                //var rupees = getString(R.string.Rs) + binding.textView14.text + getString(R.string.slash)
                val decodedString: ByteArray =
                    Base64.decode(cartData.get(position).cartImage, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                Glide.with(context)
                    .applyDefaultRequestOptions(
                        RequestOptions().placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_image_24)
                    )
                    .load(bitmap)
                    .into(cartImage)
                cartDelete.setOnClickListener {
                    val message = "Are you sure yo want to Delete Cart Item"
                    AlertDialog.Builder(context)
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton("OK") { _, _ ->
                            myCartItemClickListner.MyCartItemClickListner(
                                cartData[position],
                                position
                            )

                            //  binding.phoneContainer.helperText = getString(R.id.Required)
                        }.setNegativeButton("Cancel") { _, _ ->
                            //dismissDialog(0)
                            //  binding.phoneContainer.helperText = getString(R.id.Required)
                        }
                        .show()
                }
            }
            catch (e:Exception){
                e.toString()
            }

        }

    }
}