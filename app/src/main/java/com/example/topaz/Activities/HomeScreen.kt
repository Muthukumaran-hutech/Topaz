package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.Adapters.ArrivalsAdapter
import com.example.topaz.Adapters.HomeCategoriesAdapter
import com.example.topaz.Adapters.HomeSliderAdapter
import com.example.topaz.Adapters.OldStockListAdapter
import com.example.topaz.ApiModels.*
import com.example.topaz.Interface.HomeScreenItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.*
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.NetworkUtil
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityHomeScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    var isCategoryItemClicked=false
    var discountList=ArrayList<DiscountModel>()
    var oldstocklist = ArrayList<ArrivalsModels>()

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

                    R.id.action_category ->{val intent =Intent(activity, CategoryActivity::class.java)
                         intent.putExtra("from","Bottomnav")
                         startActivity(intent)

                    }

                }


                return true

            }
        })


        if(!NetworkUtil.checkInternet(this)){
            NetworkUtil.showNoNetworkDialog(context = this,activity = activity)
        }
        Log.d("--Network stats--",NetworkUtil.checkInternet(this).toString())



    }

    override fun onResume() {
        super.onResume()
        bottomnav.selectedItemId=R.id.action_home
       /* onAddvertisementCall()
        onApiCall()
        onApicallcat()*/
        if(isCategoryItemClicked){
            homeCategoriesAdapter.resetCategorySelection()
            isCategoryItemClicked=false
            onApiCall()
            onApicallcat()

        }
    }


    //Category API Call
    private fun onApicallcat() {
        try {
            catSubModels.clear()
            val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            res.viewCategory().enqueue(object : Callback<List<CategoryListApiModel>?> {
                override fun onResponse(
                    call: Call<List<CategoryListApiModel>?>,
                    response: Response<List<CategoryListApiModel>?>
                ) {
                    if (response.isSuccessful) {
                        //binding.appProgressBar2?.visibility = View.GONE
                        for (subCatListModel in response.body()!!) {
                            val subCatListModels = SubCatListModels(
                                // subCatListModels.categoryimage.imagebyte,
                                isCategoryClicked = false,
                                subCatListModel.categoryName,
                                subCatListModel.categoryid,
                            )


                            if (subCatListModel.active) {
                                catSubModels.add(subCatListModels)
                            }
                        }
                        //arrivals Adapter
                        try {
                            homeCategoriesAdapter =
                                HomeCategoriesAdapter(catSubModels, this@HomeScreen)
                            binding.homeRecyclerView?.adapter = homeCategoriesAdapter
                            binding.homeRecyclerView?.setHasFixedSize(true)
                        } catch (e: Exception) {
                            Log.e("Home Exception", e.toString())
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
        catch (e:Exception){
            e.toString()
        }
    }



    private fun onAddvertisementCall() {
        try {
            addlist.clear()
            var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            res.viewAdvertisement().enqueue(object : Callback<List<AdvertisementApiModel>?> {
                override fun onResponse(
                    call: Call<List<AdvertisementApiModel>?>,
                    response: Response<List<AdvertisementApiModel>?>
                ) {
                    if (response.isSuccessful) {
                        // Log.d(TAG, "add success advertisement: " + response.body()?.get(0)?.advertisementid)

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
        catch (e:Exception){
            e.toString()
        }

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
        binding.homescreenProgress?.visibility= View.VISIBLE
        arrival_list.clear()
        val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewProductList().enqueue(object : Callback<List<ProductDetailsListApiModel>?> {
            override fun onResponse(
                call: Call<List<ProductDetailsListApiModel>?>,
                response: Response<List<ProductDetailsListApiModel>?>
            ) {

                binding.homescreenProgress?.visibility=View.GONE
                if (response.isSuccessful) {

                    for (arrivalsmodels in response.body()!!) {
                        if (arrivalsmodels.collection.equals("plywood")) {
                            val arrivalsModels = ArrivalsModels(
                                arrivalsmodels.categoryType.categoryimage.imagebyte,
                                arrivalsmodels.productTitle,
                                arrivalsmodels.productid.toString(),
                                arrivalsmodels.createdDate,
                                "",
                                arrivalsmodels.sqFeetPrice
                            )

                            arrival_list.add(arrivalsModels)


                        }
                        else{
                            val arrivalsModels = ArrivalsModels(
                                arrivalsmodels.categoryType.categoryimage.imagebyte,
                                arrivalsmodels.productTitle,
                                arrivalsmodels.productid.toString(),
                                arrivalsmodels.createdDate,
                                "",
                                arrivalsmodels.sqFeetPrice
                            )
                            oldstocklist.add(arrivalsModels)

                        }


                    }

                    if(arrival_list.size >0){
                        getDiscountList()
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
                binding.homescreenProgress?.visibility= View.GONE

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
        var quoteitem = menu.findItem(R.id.quotationStatus)


        var actionview=menuitem.actionView
        var quoteactionview= quoteitem.actionView

        var cartcount=actionview.findViewById<TextView>(R.id.cart_count)//Getting the textview reference from action layout defined for the menu item
        var cart=actionview.findViewById<ImageView>(R.id.cart_icon)

        var quotecount = quoteactionview.findViewById<TextView>(R.id.quotation_count)
        var quotationview = quoteactionview.findViewById<ImageView>(R.id.quotationstatus_icon)

        setupCartCount(cartcount, cart)
        setUpQuoteListCount(quotecount,quotationview)
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

    private fun setUpQuoteListCount(quoteTextView: TextView,quoteitem:ImageView){


        quoteitem.setOnClickListener {
          startActivity(Intent(this,ProductQuotation::class.java))
        }


        Util.getQuoteListEntry(this,object : Util.QuotationCountListener {
            override fun getQuotationCount(quotationsize: Int) {

                if(quotationsize==0){
                    quoteTextView.visibility= View.GONE
                }
                else{
                    quoteTextView.visibility = View.VISIBLE
                    quoteTextView.text  = quotationsize.toString()

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
       /* val intent =Intent(activity, CategoryActivity::class.java)
        intent.putExtra("cat__iD", homecategory.catID)
        intent.putExtra("category_name",homecategory.catTitle)
        intent.putExtra("from","Homepage")
        startActivity(intent)*/
        isCategoryItemClicked=true
        homeCategoriesAdapter.changeClickedState(position,true)
        val intent = Intent(activity, InnerCategories::class.java)
        intent.putExtra("cat_id", homecategory.catID)
        intent.putExtra("cat_name", homecategory.catTitle)
        startActivity(intent)


    }


    fun getDiscountList(){
        try{
            binding.homescreenProgress?.visibility= View.VISIBLE
            val discountres = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            discountres.getAllDiscount().enqueue(object : Callback<List<DiscountListApiModel>?> {
                override fun onResponse(
                    call: Call<List<DiscountListApiModel>?>,
                    response: Response<List<DiscountListApiModel>?>
                ) {
                    binding.homescreenProgress?.visibility= View.GONE
                    if(response.isSuccessful){
                        for(discountlist in response.body()!!){
                            val discountModel = DiscountModel(
                                discountId = discountlist.discountId,
                                discount = discountlist.discount,
                                productId = discountlist.productdetail?.productId,
                                squarefeetprice = discountlist.squareFeetPrice
                            )
                            discountList.add(discountModel)
                        }

                        mapDiscountToNewArrivalProducts()

                    }
                    else{
                          initializeArrivalListAdapter()
                          initializeOldStockList()

                    }


                }

                override fun onFailure(call: Call<List<DiscountListApiModel>?>, t: Throwable) {
                Log.e("Discount list Failure","true")
                    binding.homescreenProgress?.visibility= View.GONE

                }
            })




        }
        catch (e:Exception){
            e.toString()
        }


    }

    private fun mapDiscountToNewArrivalProducts() {
        try{

            for( i in arrival_list.indices){
                addDiscountToRespectiveProduct(i,arrival_list[i])
            }


        }
        catch (e:Exception){
            e.toString()
        }
    }

    private fun addDiscountToRespectiveProduct(i: Int, arrivalsModels: ArrivalsModels) {
        try{

            for(discount in discountList){

                if(discount.productId==arrivalsModels.ArrivalId.toInt()){
                    arrival_list[i].Discount=discount.discount!!
                }
            }

            initializeArrivalListAdapter()
            initializeOldStockList()




        }
        catch(e:Exception){
            e.toString()
        }
    }

    private fun initializeArrivalListAdapter(){
        try{
            binding.arrivalsRecycler.layoutManager =LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
               // GridLayoutManager(this@HomeScreen, 2)
            val arrivalAdapter =
                ArrivalsAdapter(arrival_list, this@HomeScreen, this@HomeScreen)
            binding.arrivalsRecycler.adapter = arrivalAdapter
            binding.arrivalsRecycler.setHasFixedSize(true)



        }
        catch (e:Exception){
            e.toString()
        }
    }

    private fun initializeOldStockList(){

        try{
            if(oldstocklist.size > 0){
                binding.arrivalListSeparator?.visibility= View.VISIBLE
            }
            else{
                binding.arrivalListSeparator?.visibility= View.GONE
            }
            binding.oldstocksRecycler?.layoutManager = GridLayoutManager(this,3)
            val arrivalAdapter =
                OldStockListAdapter(oldstocklist, this@HomeScreen, this@HomeScreen)
            binding.oldstocksRecycler?.adapter = arrivalAdapter
            binding.oldstocksRecycler?.setHasFixedSize(true)




        }
        catch (e:Exception){
            e.toString()
        }

    }

}