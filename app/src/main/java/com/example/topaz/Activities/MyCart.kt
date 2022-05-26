package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Interface.IncreementDecreementItemClickListner
import com.example.topaz.Interface.MyCartItemClickListner
import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.example.topaz.Models.ProductDetailsModel
import com.example.topaz.Models.ProductQuotationsModel
import com.example.topaz.databinding.ActivityMyCartBinding
import com.google.firebase.database.*

class MyCart : AppCompatActivity(), MyCartItemClickListner,IncreementDecreementItemClickListner {

    private lateinit var binding: ActivityMyCartBinding
    lateinit var activity: Activity
    var cartData = java.util.ArrayList<CartProductList>()
    private lateinit var database: DatabaseReference
    private lateinit var database1: DatabaseReference
    var custId = ""
    private lateinit var cartAdapter:MyCartAdapter
    var productList = ArrayList<ProductQuotationsModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appProgressBar2.visibility = View.VISIBLE

        binding.linearLayout3.visibility = View.GONE
        binding.chckbtn.visibility = View.GONE
        binding.cartRecycle.visibility = View.GONE
        binding.cartEmpty.visibility = View.VISIBLE

     /*val price =   binding.priceentry.setText(cartData.get(0).price)
      binding.discountentry.setText("")
      binding.deliverychargesentry.setText("")
       // val tot = price + discount + deliveryCharge
        binding.totalamtentry.text = price.toString()*/

        activity = this
        setSupportActionBar(binding.cartListToolbar)
        supportActionBar?.title = ""

        binding.backarrow2.setOnClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }



        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()
        retrieveDataFirebase()
        binding

        binding.cartRecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }


    private fun retrieveDataFirebase() {

        database = FirebaseDatabase.getInstance().getReference("MyCart")
        val query = database.child(custId.toString()).orderByChild("cartActive").equalTo(true)


        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap: DataSnapshot in snapshot.children) {
                        val mylistdata = snap.getValue(CartList::class.java) as CartList
                        // cartData.add(mylistdata)
                        Log.d("Mycartm", mylistdata.custId)
                        getCartItems(mylistdata.cartId)
                        //Write a function to get products from Cart
                        //fun_name(cart_id){    }

                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getCartItems(cartId: String) {
        database1 = FirebaseDatabase.getInstance().getReference("MyCartProducts")
        val query = database1.child(cartId).child("Products").orderByChild("active").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartData.clear()
                if (snapshot.exists()) {
                    for (snap: DataSnapshot in snapshot.children) {
                        val mylistdata1 =
                            snap.getValue(CartProductList::class.java) as CartProductList
                        cartData.add(mylistdata1)
                        cartAdapter =
                            MyCartAdapter(cartData, this@MyCart, this@MyCart,this@MyCart)
                        binding.cartRecycle.adapter = cartAdapter
                        binding.cartRecycle.setHasFixedSize(true)
                        Log.d("productid", mylistdata1.product_id)
                        //Write a function to get products from Cart
                        //fun_name(cart_id){    }

                    }
                    if(cartData.size>0){
                        binding.appProgressBar2.visibility =View.GONE
                        binding.linearLayout3.visibility = View.VISIBLE
                        binding.chckbtn.visibility = View.VISIBLE
                        binding.cartRecycle.visibility = View.VISIBLE
                        binding.cartEmpty.visibility = View.GONE
                    }
                }
                else{

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }




    override fun MyCartItemClickListner(data: CartProductList) {

        if (cartData.size == 1) {
            database.child(custId).child(data.cart_id).child("cartActive").setValue(false)
            cartData.remove(data)
            cartAdapter.notifyItemRemoved(0)
            database1.child(data.cart_id).child("Products").child(data.product_id).child("active").setValue(false)

            binding.linearLayout3.visibility = View.GONE
            binding.chckbtn.visibility = View.GONE
            binding.cartRecycle.visibility = View.GONE
            binding.cartEmpty.visibility = View.VISIBLE

        } else {
            //Write a function which changes the status of the product in MyCrtProducts
        }

        Toast.makeText(this, "Removed from the MyCart", Toast.LENGTH_LONG).show()
    }

    override fun IncreementDecreementItemClickListner(data: CartProductList, quantity: Int) {
        Log.d(TAG, "IncreementDecreementItemClickListner: "+quantity.toString())
        updateQuantityToFirebase(data,quantity)
    }

    private fun updateQuantityToFirebase(cartData: CartProductList, quantity: Int) {
        database1.child(cartData.cart_id).child("Products").child(cartData.product_id).child("quantity").setValue(quantity.toString())

    }
}