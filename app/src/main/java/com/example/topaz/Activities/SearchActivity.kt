package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.Adapters.SearchViewAdapter
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivitySearchBinding
import java.util.*
import kotlin.collections.ArrayList

class
SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchViewAdapter
    lateinit var activity: Activity
    var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchProduct.visibility = View.VISIBLE

        activity = this
        searchAdapter = SearchViewAdapter()

        binding.userSearchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.userSearchList.adapter = searchAdapter
        binding.userSearchList.setHasFixedSize(true)

        binding.wrong.setOnClickListener {
            binding.userSearchList.clearFocus()
        }

        binding.searchBackArrow.setOnClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }

        getUserData()

        val names = ""

        binding.searchBackHome.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
var search = binding.searchEdit
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               //binding.searchEdit.clearFocus()
                if (names.isEmpty()){
                    binding.searchProduct.visibility = View.VISIBLE
                }else if (list.size == 0){
                    binding.productNotFound.visibility = View.VISIBLE
                    binding.searchProduct.visibility = View.GONE
                }else{

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (names.isEmpty()){
                    binding.searchProduct.visibility = View.VISIBLE
                }else if (list.size == 0){
                    binding.productNotFound.visibility = View.VISIBLE
                    binding.searchProduct.visibility = View.GONE
                }else{

                }

                return true
            }
        })
    }

    private fun getUserData() {



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