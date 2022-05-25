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
    private lateinit var cartAdapter: MyCartAdapter
    var cartData = java.util.ArrayList<CartProductList>()
    private lateinit var database: DatabaseReference
    var custId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this
        activity = this
        setSupportActionBar(binding.cartListToolbar)
        supportActionBar?.title = ""
        retrieveDataFirebase()


        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()





        cartAdapter = MyCartAdapter(cartData, this, this)

        binding.cartRecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cartRecycle.adapter = cartAdapter


    }

    private fun retrieveDataFirebase() {
        database = FirebaseDatabase.getInstance().getReference("MyCart")
        var query = database.child(custId).orderByChild("addedToMyCart").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap: DataSnapshot in snapshot.children) {
                        val mylistdata = snap.getValue(CartList::class.java) as CartProductList
                        cartData.add(mylistdata)
                       // Log.d("MycartD", mylistdata.custId)
                    }

                    val cartAdapter =
                        MyCartAdapter(cartData, this@MyCart, this@MyCart)
                    binding.cartRecycle.adapter = cartAdapter
                    binding.cartRecycle.setHasFixedSize(true)

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
       /* database.child(custId).child(data.productid.toString()).child("addedToWishList").setValue(false)
        cartData.remove(data)*/
        Toast.makeText(this,"Removed from the wishlist", Toast.LENGTH_LONG).show()
    }
}