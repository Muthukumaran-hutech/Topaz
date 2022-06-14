package com.example.topaz.Utility

import android.content.Context
import android.util.Log
import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object Util {

    interface CartCountListener{
        fun getCartCount(cartsize:Int)
    }


    fun getCartCount( context: Context,cartCountListener: CartCountListener){
        val sharedPreference = context.getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        val custid= sharedPreference.getString("customercode", "").toString()
        val database= FirebaseDatabase.getInstance().getReference("MyCart")
        val query = database.child(custid.toString()).orderByChild("cartActive").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){

                   for (snap: DataSnapshot in snapshot.children) {
                       val mylistdata = snap.getValue(CartList::class.java) as CartList
                       // cartData.add(mylistdata)
                       Log.d("Mycartm", mylistdata.custId)
                       getCartItems(mylistdata.cartId,cartCountListener)
                       //Write a function to get products from Cart
                       //fun_name(cart_id){    }

                   }
               }
                else{
                    cartCountListener.getCartCount(0)
               }

            }

            override fun onCancelled(error: DatabaseError) {
             cartCountListener.getCartCount(0)

            }
        })

    }

    private fun getCartItems(cartId: String,cartCountListener: CartCountListener) {
        val cartData = java.util.ArrayList<CartProductList>()
        val database1 = FirebaseDatabase.getInstance().getReference("MyCartProducts")
        val query = database1.child(cartId).child("Products").orderByChild("active").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartData.clear()
                if(snapshot.exists()){


                    for(snap:DataSnapshot in snapshot.children){
                        val mylistdata1 =
                            snap.getValue(CartProductList::class.java) as CartProductList
                        cartData.add(mylistdata1)
                    }

                    cartCountListener.getCartCount(cartsize = cartData.size)



                }
                else{
                    cartCountListener.getCartCount(0)
                }

            }

            override fun onCancelled(error: DatabaseError) {

                cartCountListener.getCartCount(0)
            }
        })

    }
}