package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.list
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityAccountInformationBinding
import com.example.topaz.databinding.ActivityHomeScreenBinding
import java.util.ArrayList

class HomeScreen : AppCompatActivity(), HomeScreenItemClickListner {

    private lateinit var binding: ActivityHomeScreenBinding

    //private lateinit var adapter: HomeCategoriesAdapter
    lateinit var activity: Activity
    val imageList = ArrayList<Int>()
    var slidemodellist= ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list.add(HomeCategoryModel("","Plywoods"))
        val adapter:HomeCategoriesAdapter = HomeCategoriesAdapter(list1 = list,this)
        activity = this
        setSupportActionBar(binding.homeToolbar)


        binding.homeRecyclerView?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.homeRecyclerView?.adapter = adapter


        imageList.add(R.drawable.home_slider_banner)
        imageList.add(R.drawable.home_slider_banner_2)


        //-----------------Slide model list---------------------//
        slidemodellist.add(SlideModel(imagePath = R.drawable.home_slider_banner))
        slidemodellist.add(SlideModel(imagePath = R.drawable.home_slider_banner_2))

        binding.homeImageSlider.setImageList(slidemodellist,ScaleTypes.FIT)

     /*   binding.homeSearchBtn.setOnClickListener{
            startActivity(Intent(activity,SearchActivity::class.java))
            finish()
        }

        binding.homeCartBtn.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))
            finish()
        }

        binding.homeNotificationBtn.setOnClickListener{
            Toast. makeText(applicationContext," Currently In Process",Toast. LENGTH_SHORT).show()
        }*/

        binding.home.setOnClickListener{
            Toast. makeText(applicationContext,"You Are Currently In Home Page",Toast. LENGTH_SHORT).show()
        }

        binding.categories.setOnClickListener{
            startActivity(Intent(activity,CategoryActivity::class.java))
            finish()
        }

        binding.account.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }

        binding.fav.setOnClickListener{
            startActivity(Intent(activity,MyWishlist::class.java))
            finish()
        }

    }

    override fun HomeScreenItemClickListner(homecategory:HomeCategoryModel) {
        startActivity(Intent(activity,CategoryActivity::class.java))
        finish()
    }


}