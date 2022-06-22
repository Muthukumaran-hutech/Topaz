package com.example.topaz.Utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.example.topaz.Models.QuatationDetailModel
import com.example.topaz.Models.QuatationListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object Util {

    interface CartCountListener{
        fun getCartCount(cartsize:Int)
    }


    interface QuotationCountListener{
         fun getQuotationCount(quotationsize:Int)
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


    public fun extractNumbersFromString(input:String,type:String):List<String>{
         var list= listOf<String>()
        try{
            if(type.equals("Percentage")) {
                list = Regex("\\D+").split(input)
            }
            else{
                var size1=input.split("*")
                list=(size1[0]+size1[1]).split("ft")

            }


        }
        catch (e:Exception){
            e.printStackTrace()
            list= listOf<String>(" ")

        }

        return list

    }

    public fun getBitmapFromBase64(path:String):Bitmap?{
        var bitmap:Bitmap?=null
        try{
            var decodedstring=Base64.decode(path, Base64.DEFAULT)
             bitmap= BitmapFactory.decodeByteArray(decodedstring,0,decodedstring.size)


        }
        catch (e:Exception){
            e.printStackTrace()
        }

        return bitmap
    }


    fun getQuoteListEntry(context: Context,quotationCountListener: QuotationCountListener){
        try {
            val sharedPreference =
                context.getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
            val custid = sharedPreference.getString("customercode", "").toString()
            val quoteref = FirebaseDatabase.getInstance().getReference("MyQuotation")
            var query = quoteref.child(custid).orderByChild("quotationStatus").equalTo(true)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(snap in snapshot.children){
                            var quatationListModel=snap.getValue(QuatationDetailModel::class.java)
                            fetchProducts(quatationListModel?.quotationId,quotationCountListener)
                        }
                    }
                    else{
                        quotationCountListener.getQuotationCount(0)
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
        catch (e:Exception){
            e.toString()
        }


    }

    private fun fetchProducts(quotationId: String?, quotationCountListener: QuotationCountListener) {
        try{
            val quotationlist=ArrayList<QuatationListModel>()
            val prdref = FirebaseDatabase.getInstance().getReference("QuotationProductList")
            quotationId?.let {
                prdref.child(it).child("Products").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        quotationlist.clear()
                        if(snapshot.exists()){
                            for(snap in snapshot.children){
                                var quotationListModel=snap.getValue(QuatationListModel::class.java)!!
                                quotationlist.add(quotationListModel)

                            }
                            quotationCountListener.getQuotationCount(quotationsize = quotationlist.size)
                        }
                        else{
                            quotationCountListener.getQuotationCount(0)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        quotationCountListener.getQuotationCount(0)
                    }
                })
            }



        }
        catch (e:Exception){
            e.toString()
        }

    }
}