package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.Interface.WishListItemClickListner
import com.example.topaz.Models.DetailsFirebaseModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityMyWishlistBinding
import com.google.firebase.database.*

class MyWishlist : AppCompatActivity(), WishListItemClickListner {

    private lateinit var binding: ActivityMyWishlistBinding
    lateinit var activity: Activity
    var wishData = java.util.ArrayList<DetailsFirebaseModel>()
    private lateinit var database: DatabaseReference
    var custId = ""
    private lateinit var wishlistAdapter : MywishlistAdapter

    //private lateinit var wishlistAdapter: MywishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        setSupportActionBar(binding.wishListToolbar)
        supportActionBar?.title = ""


        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()
        retrieveDataFirebase()
        // wishlistAdapter = MywishlistAdapter()
        binding.wishlistRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)




        binding.backarrow2.setOnClickListener {
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
    }

    private fun retrieveDataFirebase() {
        database = FirebaseDatabase.getInstance().getReference("WhishList")
        var query = database.child(custId).orderByChild("addedToWishList").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                wishData.clear()
                if (snapshot.exists()) {

                    for (snap: DataSnapshot in snapshot.children) {
                        val wishlistdata = snap.getValue(DetailsFirebaseModel::class.java) as DetailsFirebaseModel

                        wishData.add(wishlistdata)
                        Log.d("Wishdata", wishlistdata.custId)

                    }

                    //Set data to recyclerview

                     wishlistAdapter =
                        MywishlistAdapter(wishData, this@MyWishlist, this@MyWishlist)
                    binding.wishlistRecycler.adapter = wishlistAdapter
                    binding.wishlistRecycler.setHasFixedSize(true)

                }else{

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mywishlist_page_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }


    override fun WishListItemClickListner(data: DetailsFirebaseModel, position: Int) {
        wishData.remove(data)
        wishlistAdapter.notifyItemRemoved(position)
        database.child(custId).child(data.productid.toString()).child("addedToWishList").setValue(false)
        Toast.makeText(this,"Removed from the wishlist",Toast.LENGTH_LONG).show()
    }
}