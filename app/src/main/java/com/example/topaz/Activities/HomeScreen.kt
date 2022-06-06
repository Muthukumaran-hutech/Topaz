package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.Adapters.ArrivalsAdapter
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.ApiModels.AdvertisementApiModel
import com.example.topaz.ApiModels.ArrivalsPageItemClickListner
import com.example.topaz.ApiModels.CategoryListApiModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.*
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityHomeScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeScreen : AppCompatActivity(), HomeScreenItemClickListner, ArrivalsPageItemClickListner {

    private lateinit var binding: ActivityHomeScreenBinding

    // private lateinit var arrivalAdapter: ArrivalsAdapter

    lateinit var activity: Activity
    val imageList = ArrayList<Int>()
    var slidemodellist = ArrayList<SlideModel>()
    var categorylist = java.util.ArrayList<HomeCategoryModel>()
    var arrival_list = ArrayList<ArrivalsModels>()
    var product = AddModels()
    var addlist = java.util.ArrayList<AddModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* list.add(HomeCategoryModel("","Plywoods"))
         val adapter:HomeCategoriesAdapter = HomeCategoriesAdapter(list1 = list,this)*/
        activity = this
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.title = ""


        binding.homeRecyclerView?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //------------Category API--------------------
        categorylist.add(HomeCategoryModel("", "Plywood"))
        categorylist.add(HomeCategoryModel("", "Laminates"))
        categorylist.add(HomeCategoryModel("", "Veneers"))
        var homeCategoriesAdapter = HomeCategoriesAdapter(categorylist, this)
        binding.homeRecyclerView?.adapter = homeCategoriesAdapter
        //binding.homeRecyclerView?.adapter = adapter

        onAddvertisementCall()

        /*  imageList.add(R.drawable.home_slider_banner)
          imageList.add(R.drawable.home_slider_banner_2)*/


        //-----------------Slide model list---------------------//
        /* slidemodellist.add(SlideModel(imagePath = R.drawable.home_slider_banner))
         slidemodellist.add(SlideModel(imagePath = R.drawable.home_slider_banner_2))*/

         binding.homeImageSlider.setImageList(slidemodellist, ScaleTypes.FIT)


        onApiCall()

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

        binding.home.setOnClickListener {
            Toast.makeText(applicationContext, "You Are Currently In Home Page", Toast.LENGTH_SHORT)
                .show()
        }

        binding.categories.setOnClickListener {
            startActivity(Intent(activity, CategoryActivity::class.java))
            //finish()
        }

        binding.account.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))
            //finish()
        }

        binding.fav.setOnClickListener {
            startActivity(Intent(activity, MyWishlist::class.java))
            //finish()
        }

    }

    private fun onAddvertisementCall() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewAdvertisement().enqueue(object : Callback<List<AdvertisementApiModel>?> {
            override fun onResponse(
                call: Call<List<AdvertisementApiModel>?>,
                response: Response<List<AdvertisementApiModel>?>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "add success advertisement: " + response.body()?.get(0)?.advertisementid)

                    for (addvertisement in response.body()!!) {
                        var addModel = AddModels(
                            addvertisement.advertismentImage.imagepath,
                            addvertisement.title
                        )
                        addlist.add(addModel)
                    }

                } else {
                    Log.d(TAG, "add fail advertisement: " + response.body())
                }

            }

            override fun onFailure(call: Call<List<AdvertisementApiModel>?>, t: Throwable) {
                Log.d(TAG, "add Failure advertisement: " + t.message)
            }
        })

    }

    private fun onApiCall() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewCategory().enqueue(object : Callback<List<CategoryListApiModel>?> {
            override fun onResponse(
                call: Call<List<CategoryListApiModel>?>,
                response: Response<List<CategoryListApiModel>?>
            ) {
                if (response.isSuccessful) {
                    binding.appProgressBar2?.visibility = View.GONE
                    for (arrivalsModels in response.body()!!) {
                        val arrivalsModels = ArrivalsModels(
                            arrivalsModels.categoryimage.imagebyte,
                            arrivalsModels.categoryName,
                            arrivalsModels.categoryid,
                            arrivalsModels.createdDate
                        )


                        arrival_list.add(arrivalsModels)
                        try {
                            var simpleDateFormat = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss.SSSZ",
                                Locale.getDefault()
                            )
                            var arivalDate =
                                simpleDateFormat.parse(arrival_list.get(0).ArrivalCreatedDate)
                            var currentDate = java.util.Calendar.getInstance().time

                            var dateDifference = currentDate.time - arivalDate.time
                            Log.d(TAG, "get Time: " + dateDifference)
                        } catch (e: Exception) {
                            Log.d(TAG, "get Time: fail " + e)
                        }
                    }
                    //arrivals Adapter

                    binding.arrivalsRecycler.layoutManager = GridLayoutManager(this@HomeScreen, 2)
                    val arrivalAdapter =
                        ArrivalsAdapter(arrival_list, this@HomeScreen, this@HomeScreen)
                    binding.arrivalsRecycler.adapter = arrivalAdapter
                    binding.arrivalsRecycler.setHasFixedSize(true)

                    Log.d(
                        ContentValues.TAG,
                        "onResponse: arrival Success" + response.body()
                            ?.get(0)?.tags?.get(0)?.tags.toString()
                    )
                } else {
                    binding.appProgressBar2?.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wronng Please Try Again Later",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.d(
                        ContentValues.TAG,
                        "onResponse: arrival Fail: " + response.body()
                            ?.get(0)?.tags?.get(0)?.tags.toString()
                    )
                }


            }

            override fun onFailure(call: Call<List<CategoryListApiModel>?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onResponse: arrival failure: " + t.message)
            }
        })


    }

    override fun ArrivalsPageItemClickListner(arrivals: ArrivalsModels) {
        startActivity(Intent(activity, CategoryActivity::class.java))
    }

    /* override fun ArrivalsPageItemClickListner(arrivals: ArrivalsModels){
         startActivity(Intent(activity, InnerCategories::class.java))
     }
  */

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
                // dismissDialog(0)
                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }
            .show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.homepagemenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

    override fun HomeScreenItemClickListner(homecategory: HomeCategoryModel) {
        startActivity(Intent(activity, CategoryActivity::class.java))
        finish()
    }


}