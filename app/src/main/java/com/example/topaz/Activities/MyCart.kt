package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.R
import com.example.topaz.databinding.ActivityEditProfileBinding
import com.example.topaz.databinding.ActivityMyCartBinding

class MyCart : AppCompatActivity() {

    private lateinit var binding: ActivityMyCartBinding
    lateinit var activity: Activity
    private lateinit var cartAdapter: MyCartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        activity = this

        cartAdapter = MyCartAdapter()

        binding.cartrecycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.cartrecycle.adapter = cartAdapter





        binding.search.setOnClickListener{
            startActivity(Intent(activity,SearchActivity::class.java))

        }

      /*  myCartBtn?.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))

        }*/

        binding.notification.setOnClickListener{
            startActivity(Intent(activity,Notifications::class.java))

        }

        binding.backarrow.setOnClickListener{
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
                //dismissDialog(0)
                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }
            .show()
    }
}