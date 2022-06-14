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
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.Interface.WishListItemClickListner
import com.example.topaz.Models.DetailsFirebaseModel
import com.example.topaz.R
import com.example.topaz.Utility.Util
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
        binding.wishlistProgress.visibility=View.VISIBLE
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
                    binding.wishlistProgress.visibility=View.GONE
                    if(wishData.size==0){
                        binding.wishlistNoDataText.visibility= View.VISIBLE
                    }
                    else{
                        binding.wishlistNoDataText.visibility= View.GONE
                    }
                     wishlistAdapter =
                        MywishlistAdapter(wishData, this@MyWishlist, this@MyWishlist)
                    binding.wishlistRecycler.adapter = wishlistAdapter
                    binding.wishlistRecycler.setHasFixedSize(true)

                }else{
                    binding.wishlistProgress.visibility=View.GONE
                    binding.wishlistNoDataText.visibility= View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                binding.wishlistProgress.visibility=View.GONE
                Toast.makeText(this@MyWishlist,"Something went wrong",Toast.LENGTH_LONG).show()

            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.homepagemenu, menu)
        var menuitem=menu.findItem(R.id.my_cart)
        var actionview=menuitem.actionView
        var cartcount=actionview.findViewById<TextView>(R.id.cart_count)//Getting the textview reference from action layout defined for the menu item
        var cart=actionview.findViewById<ImageView>(R.id.cart_icon)
        setupCartCount(cartcount, cart)
        return true
    }

    private fun setupCartCount(cartcount: TextView?, cart: ImageView?) {

        cart?.setOnClickListener {
            startActivity(Intent(activity, MyCart::class.java))
        }


        Util.getCartCount(context = this,object : Util.CartCountListener {//Gets the cart count
        override fun getCartCount(cartsize: Int) {
            //Get the cart count entries
            Log.d("--Cart count--",cartsize.toString())
            if(cartsize==0) {
                cartcount?.visibility=View.GONE
            }else {
                cartcount?.visibility=View.VISIBLE
                cartcount?.text = cartsize.toString()
            }

        }
        })


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