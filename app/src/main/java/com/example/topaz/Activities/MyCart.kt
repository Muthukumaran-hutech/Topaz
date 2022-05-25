package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Interface.MyCartItemClickListner
import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.example.topaz.databinding.ActivityMyCartBinding
import com.google.firebase.database.*

class MyCart : AppCompatActivity(), MyCartItemClickListner {

    private lateinit var binding: ActivityMyCartBinding
    lateinit var activity: Activity
    var cartData = java.util.ArrayList<CartProductList>()
    private lateinit var database: DatabaseReference
    private lateinit var database1: DatabaseReference
    var custId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        setSupportActionBar(binding.cartListToolbar)
        supportActionBar?.title = ""


        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()
        retrieveDataFirebase()

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
        val query = database1.child(cartId).child("Products")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap: DataSnapshot in snapshot.children) {
                        val mylistdata1 =
                            snap.getValue(CartProductList::class.java) as CartProductList
                        cartData.add(mylistdata1)
                        val cartAdapter =
                            MyCartAdapter(cartData, this@MyCart, this@MyCart)
                        binding.cartRecycle.adapter = cartAdapter
                        binding.cartRecycle.setHasFixedSize(true)
                        Log.d("productid", mylistdata1.product_id)
                        //Write a function to get products from Cart
                        //fun_name(cart_id){    }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val message = "Are you sure yo want to exit"
        AlertDialog.Builder(this)
            .setTitle("Applcation will be logged out ")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                super.onBackPressed()

                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }.setNegativeButton("Cancel") { _, _ ->
                //dismissDialog(0)
                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }
            .show()
    }

    override fun MyCartItemClickListner(data: CartProductList) {

        if (cartData.size == 1) {
            database.child(custId).child(data.cart_id).child("cartActive").setValue(false)
            cartData.remove(data)

        } else {
            //Write a function which changes the status of the product in MyCrtProducts
        }

        Toast.makeText(this, "Removed from the MyCart", Toast.LENGTH_LONG).show()
    }
}