package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.R
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityMyWishlistBinding

class MyWishlist : AppCompatActivity() {

    private lateinit var binding: ActivityMyWishlistBinding
    lateinit var activity: Activity

    private lateinit var wishlistAdapter: MywishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        setSupportActionBar(binding.wishListToolbar)
        supportActionBar?.title = ""

        wishlistAdapter = MywishlistAdapter()

        binding.wishlistadapter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.wishlistadapter.adapter = wishlistAdapter

        binding.backarrow2.setOnClickListener {
            startActivity(Intent(activity, HomeScreen::class.java))
            finish()
        }
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
                dismissDialog(0)
                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }
            .show()
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
}