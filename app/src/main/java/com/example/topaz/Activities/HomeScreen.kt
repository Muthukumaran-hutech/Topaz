package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.Adapters.ArrivalsAdapter
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.HomeSliderAdapter
import com.example.topaz.ApiModels.AdvertisementApiModel
import com.example.topaz.ApiModels.ArrivalsPageItemClickListner
import com.example.topaz.ApiModels.CategoryListApiModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.*
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityHomeScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
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
    var catSubModels = ArrayList<SubCatListModels>()
    var product = AddModels()
    var addlist = java.util.ArrayList<AddModels>()
    var bitmap = java.util.ArrayList<Bitmap>()
    lateinit var homeCategoriesAdapter:HomeCategoriesAdapter
    lateinit var bottomnav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* list.add(HomeCategoryModel("","Plywoods"))
         val adapter:HomeCategoriesAdapter = HomeCategoriesAdapter(list1 = list,this)*/
        activity = this
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.title = ""

       /* binding.scrl.isEnabled = false*/



        binding.homeRecyclerView?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //------------Category API--------------------
       /* categorylist.add(HomeCategoryModel("", "Plywood"))
        categorylist.add(HomeCategoryModel("", "Laminates"))
        categorylist.add(HomeCategoryModel("", "Veneers"))*/

        //binding.homeRecyclerView?.adapter = adapter

        onAddvertisementCall()


         binding.homeImageSlider.setImageList(slidemodellist, ScaleTypes.FIT)


        onApiCall()
        onApicallcat()


        /*binding.home?.setOnClickListener {
            Toast.makeText(applicationContext, "You Are Currently In Home Page", Toast.LENGTH_SHORT)
                .show()
        }

        binding.categories?.setOnClickListener {
            startActivity(Intent(activity, CategoryActivity::class.java))
            //finish()
        }

        binding.account?.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))
            //finish()
        }

        binding.fav?.setOnClickListener {
            startActivity(Intent(activity, MyWishlist::class.java))
            //finish()
        }*/


        bottomnav=binding.bottomNavLayout!!.bottomNavItem

        bottomnav.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){

                    R.id.action_favourite-> startActivity(Intent(activity, MyWishlist::class.java))

                    R.id.action_account->  startActivity(Intent(activity, MyAccount::class.java))
                }


                return true

            }
        })



    }

    override fun onResume() {
        super.onResume()
        bottomnav.selectedItemId=R.id.action_home
        onAddvertisementCall()
        onApiCall()
        onApicallcat()
    }


    //cat slide
    private fun onApicallcat() {
        catSubModels.clear()
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewCategory().enqueue(object : Callback<List<CategoryListApiModel>?> {
            override fun onResponse(
                call: Call<List<CategoryListApiModel>?>,
                response: Response<List<CategoryListApiModel>?>
            ) {
                if (response.isSuccessful) {
                    binding.appProgressBar2?.visibility = View.GONE
                    for (subCatListModel in response.body()!!) {
                        val subCatListModels = SubCatListModels(
                           // subCatListModels.categoryimage.imagebyte,
                            isCategoryClicked = false,
                            subCatListModel.categoryName,
                            subCatListModel.categoryid
                        )


                       if(subCatListModel.active) {
                           catSubModels.add(subCatListModels)
                       }
                    }
                    //arrivals Adapter
                    try {
                        homeCategoriesAdapter = HomeCategoriesAdapter(catSubModels, this@HomeScreen)
                        binding.homeRecyclerView?.adapter = homeCategoriesAdapter
                        binding.homeRecyclerView?.setHasFixedSize(true)
                    }
                    catch (e:Exception){
                        Log.e("Home Exception",e.toString())
                    }

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

    private fun onAddvertisementCall() {
        addlist.clear()
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
                        val addModel = AddModels(
                            addvertisement.advertismentImage.imagebyte,
                            addvertisement.title,
                            addDescription = addvertisement.discription
                        )

                        addlist.add(addModel)
                       /* val decodedString: ByteArray = Base64.decode(

                            addvertisement.advertismentImage.imagebyte,
                            Base64.DEFAULT)
                        val imageview = ImageView(activity)
                            val bitmap =
                                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                           if(bitmap!=null) {
                               imageview.setImageBitmap(bitmap)
                               binding.viewFliper.addView(imageview)
                           }
                        else{

                            imageview.setImageDrawable(AppCompatResources.getDrawable(this@HomeScreen,R.drawable.ic_baseline_image_24))
                            binding.viewFliper.addView(imageview)

                           }*/


                        /*Glide.with(activity)
                            .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_image_24))
                            .load(bitmap)
                            .into(imageview)*/




                    }

                    setSliderItems(addlist)

                } else {
                    Log.d(TAG, "add fail advertisement: " + response.body())
                }

            }

            override fun onFailure(call: Call<List<AdvertisementApiModel>?>, t: Throwable) {
                Log.d(TAG, "add Failure advertisement: " + t.message)
            }
        })

    }

    private fun setSliderItems(adlist: ArrayList<AddModels>) {

        try {

            val sliderAdapter = HomeSliderAdapter(this, adlist)
            binding.viewFliperAdapter.adapter = sliderAdapter
            binding.viewFliperAdapter.flipInterval = 5000
            binding.viewFliperAdapter.startFlipping()
        }
        catch (e:Exception){
            e.toString()
        }

    }

    private fun onApiCall() {
        arrival_list.clear()
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewProductList().enqueue(object : Callback<List<ProductDetailsListApiModel>?> {
            override fun onResponse(
                call: Call<List<ProductDetailsListApiModel>?>,
                response: Response<List<ProductDetailsListApiModel>?>
            ) {

                if (response.isSuccessful) {

                    for (arrivalsmodels in response.body()!!) {
                        if (arrivalsmodels.collection.equals("New Arrival")) {
                            val arrivalsModels = ArrivalsModels(
                                arrivalsmodels.categoryType.categoryimage.imagebyte,
                                arrivalsmodels.productTitle,
                                arrivalsmodels.productid.toString(),
                                arrivalsmodels.createdDate
                            )

                            arrival_list.add(arrivalsModels)


                        }

                        binding.arrivalsRecycler.layoutManager =
                            GridLayoutManager(this@HomeScreen, 2)
                        val arrivalAdapter =
                            ArrivalsAdapter(arrival_list, this@HomeScreen, this@HomeScreen)
                        binding.arrivalsRecycler.adapter = arrivalAdapter
                        binding.arrivalsRecycler.setHasFixedSize(true)


                    }

                } else {
                    binding.appProgressBar2?.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wrong Please Try Again Later",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }



            override fun onFailure(call: Call<List<ProductDetailsListApiModel>?>, t: Throwable) {

            }
        })

       /* res.viewCategory().enqueue(object : Callback<List<CategoryListApiModel>?> {
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
                            val simpleDateFormat = SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss.SSSZ",
                                Locale.getDefault()
                            )
                            val arivalDate =
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

*/
            //}

           /* override fun onFailure(call: Call<List<CategoryListApiModel>?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onResponse: arrival failure: " + t.message)
            }
        })*/


    }

    override fun ArrivalsPageItemClickListner(arrivals: ArrivalsModels) {
       /* startActivity(Intent(activity, CategoryActivity::class.java))
        val intent =Intent(activity, CategoryActivity::class.java)
        intent.putExtra("cat__iD", arrivals.ArrivalId)
        intent.putExtra("category_name",arrivals.ArrivalName)
        startActivity(intent)*/
        val intent = Intent(activity, ProductDetails::class.java)
        intent.putExtra("inner_sunid",arrivals.ArrivalId)
        intent.putExtra("product_name", arrivals.ArrivalName)
        startActivity(intent)
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
        var menuitem=menu.findItem(R.id.my_cart)
        var actionview=menuitem.actionView
        var cartcount=actionview.findViewById<TextView>(R.id.cart_count)//Getting the textview reference from action layout defined for the menu item
        var cart=actionview.findViewById<ImageView>(R.id.cart_icon)
        setupCartCount(cartcount, cart)
        return true
    }

    private fun setupCartCount(cartcount: TextView?, cartitem: ImageView,) {

        cartitem.setOnClickListener {
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
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java) )
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return true
    }

    override fun HomeScreenItemClickListner(homecategory: SubCatListModels, position: Int) {
       homeCategoriesAdapter.changeClickedState(position = position)
        val intent =Intent(activity, CategoryActivity::class.java)
        intent.putExtra("cat__iD", homecategory.catID)
        intent.putExtra("category_name",homecategory.catTitle)
        startActivity(intent)
    }


}