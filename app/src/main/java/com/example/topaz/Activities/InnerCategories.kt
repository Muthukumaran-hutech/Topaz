package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
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
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.ApiModels.DiscountListApiModel
import com.example.topaz.ApiModels.SubCategoryApiModel
import com.example.topaz.Interface.InnerCategoryItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.DiscountModel
import com.example.topaz.Models.InnerCategoryModelList
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityInnerCategoriesBinding
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.bottomnavigation_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InnerCategories : AppCompatActivity(), InnerCategoryItemClickListner {

    private lateinit var binding: ActivityInnerCategoriesBinding
    var inerCategorylist = java.util.ArrayList<InnerCategoryModelList>()
    lateinit var activity: Activity
    var custId = ""
    var catid = ""
    var innerCategories=SubCategoryApiModel()
    var discountList=ArrayList<DiscountModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInnerCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        val item = intent.getStringExtra("cat_id")
        val categoryname = intent.getStringExtra("cat_name")
        catid = item!!

        onApiCallInnerCategory()

        activity = this
        setSupportActionBar(binding.innerCatToolbar)
        supportActionBar?.title = ""
        binding.innerrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.backarrow2.setOnClickListener {
            finish()
            //startActivity(Intent(activity, CategoryActivity::class.java))
        }

       /* binding.label2.setOnClickListener{

        }*/
        val bottomnav=binding.innerCatBottomLayout.bottom_nav_item
        bottomnav.selectedItemId=R.id.action_category
        bottomnav.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when(item.itemId){
                    R.id.action_home-> startActivity(Intent(activity, HomeScreen::class.java))
                        R.id.action_favourite-> startActivity(Intent(activity, MyWishlist::class.java))

                    R.id.action_account->  startActivity(Intent(activity, MyAccount::class.java))
                }


                return true

            }
        })




        binding.textView9.setText(categoryname)

    }

    private fun onApiCallInnerCategory() {
        binding.innerCategoryProgress?.visibility= View.VISIBLE
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.getProductByCategory(catid).enqueue(object : Callback<List<SubCategoryApiModel>?> {
            override fun onResponse(
                call: Call<List<SubCategoryApiModel>?>,
                response: Response<List<SubCategoryApiModel>?>
            ) {

                binding.innerCategoryProgress?.visibility= View.GONE
                if(response.isSuccessful){
                    for(innercategorylist in response.body()!!){
                        val innerCategoryModelList = InnerCategoryModelList(
                            innercategorylist.Productimage1.imagebyte,
                            innercategorylist.productTitle,
                            innercategorylist.sqFeetPrice,
                            innercategorylist.thickness,
                            innercategorylist.subcategory.subid.toString(),
                            innercategorylist.productid.toString(),
                            size = innercategorylist.size

                        )
                        inerCategorylist.add(innerCategoryModelList)

                    }
                    if(inerCategorylist.size >0) {
                        binding.innerCatNoProductsFound?.visibility= View.GONE
                        getDiscountList()
                    }
                    else{
                        binding.innerCatNoProductsFound?.visibility= View.VISIBLE
                    }
                    /*val innercategoryAdapter = InnerCategoryAdapter(inerCategorylist,this@InnerCategories,this@InnerCategories)
                    binding.innerrecycler.adapter = innercategoryAdapter
                    binding.innerrecycler.setHasFixedSize(true)*/
                    //Toast. makeText(applicationContext,"Something Went Wronng Please Try Again Later", Toast.LENGTH_LONG).show()

                    Log.d(
                        TAG,
                        "On Inner: " + response.body()
                    )
                }else{
                    binding.innerCategoryProgress?.visibility= View.GONE
                    Toast. makeText(applicationContext,"NO DATA TO DISPLAY", Toast.LENGTH_LONG).show()

                    Log.d(
                        TAG,
                        "OnFail inner: " + response.body()
                    )
                }
            }

            override fun onFailure(call: Call<List<SubCategoryApiModel>?>, t: Throwable) {
                binding.innerCategoryProgress?.visibility= View.GONE
                Toast. makeText(applicationContext,"Something Went Wronng Please Try Again Later", Toast.LENGTH_LONG).show()

                Log.d(
                    TAG,
                    "OnFailure inner: " + t.message
                )
            }
        })


    }


    override fun InnerCategoryItemClickListner(Innercategories: InnerCategoryModelList) {
        val intent = Intent(activity, ProductDetails::class.java)
        intent.putExtra("inner_sunid",Innercategories.InnerCateegoryProductId)
        intent.putExtra("product_name",Innercategories.InnerCateegoryTitle)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.homepagemenu, menu)
        var menuitem=menu.findItem(R.id.my_cart)
        var actionview=menuitem.actionView
        var cartcount=actionview.findViewById<TextView>(R.id.cart_count)//Getting the textview reference from action layout defined for the menu item
        var cart=actionview.findViewById<ImageView>(R.id.cart_icon)

        var quoteitem = menu.findItem(R.id.quotationStatus)
        var quoteactionview= quoteitem.actionView
        var quotecount = quoteactionview.findViewById<TextView>(R.id.quotation_count)
        var quotationview = quoteactionview.findViewById<ImageView>(R.id.quotationstatus_icon)


        setUpQuoteListCount(quotecount,quotationview)
        setupCartCount(cartcount, cart)
        return true
    }


    private fun setUpQuoteListCount(quotecount: TextView?, quotationview: ImageView?) {
        quotationview?.setOnClickListener {
            startActivity(Intent(this,ProductQuotation::class.java))
        }


        Util.getQuoteListEntry(this,object : Util.QuotationCountListener {
            override fun getQuotationCount(quotationsize: Int) {

                if(quotationsize==0){
                    quotecount?.visibility= View.GONE
                }
                else{
                    quotecount?.visibility = View.VISIBLE
                    quotecount?.text  = quotationsize.toString()

                }

            }
        })
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
                cartcount?.visibility= View.GONE
            }else {
                cartcount?.visibility= View.VISIBLE
                cartcount?.text = cartsize.toString()
            }

        }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.notification_bar -> Toast.makeText(
                applicationContext, " Currently In Process",
                Toast.LENGTH_SHORT
            ).show()
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java) )
        }
        return super.onOptionsItemSelected(item)

    }

    private fun getDiscountList(){

        try{
            binding.innerCategoryProgress?.visibility= View.VISIBLE
            val discountres = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            discountres.getAllDiscount().enqueue(object : Callback<List<DiscountListApiModel>?> {
                override fun onResponse(
                    call: Call<List<DiscountListApiModel>?>,
                    response: Response<List<DiscountListApiModel>?>
                ) {
                    binding.innerCategoryProgress?.visibility= View.GONE
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

                        mapDiscountsToProoduct()
                    }
                    else{
                        binding.innerCategoryProgress?.visibility= View.GONE

                    }
                }

                override fun onFailure(call: Call<List<DiscountListApiModel>?>, t: Throwable) {
                    binding.innerCategoryProgress?.visibility= View.GONE

                    Log.e("Discount failure", t.toString())
                }
            })

            /*discountres.getDiscountOnProduct("5").enqueue(object : Callback<DiscountListApiModel?> {
                override fun onResponse(
                    call: Call<DiscountListApiModel?>,
                    response: Response<DiscountListApiModel?>
                ) {

                    if(response.isSuccessful){

                        Log.d("Discount success","true")

                        var discountListApiModel:DiscountListApiModel=response.body()!!
                        Log.d("discount detail",discountListApiModel.discount.toString())
                    }
                    else{
                        Log.d("Discount failure","true")

                    }

                }

                override fun onFailure(call: Call<DiscountListApiModel?>, t: Throwable) {
                    Log.d("Discount failure","true")
                }
            })*/




        }
        catch (e:Exception){
            e.printStackTrace()
            binding.innerCategoryProgress?.visibility= View.GONE
        }

    }

    private fun mapDiscountsToProoduct() {//Mapping discount to respective products

      for(i in inerCategorylist.indices){
          addDiscountToSelectedProduct(i,inerCategorylist[i].InnerCateegoryProductId)
      }
    }

    private fun addDiscountToSelectedProduct(i: Int, innerCateegoryProductId: String) {
        for(discount in discountList){
            if(discount.productId.toString().equals(innerCateegoryProductId)){
                inerCategorylist[i].discount=discount.discount
            }
        }
        val innercategoryAdapter = InnerCategoryAdapter(inerCategorylist,this@InnerCategories,this@InnerCategories)
        binding.innerrecycler.adapter = innercategoryAdapter
        binding.innerrecycler.setHasFixedSize(true)

        Log.d("Discount list",inerCategorylist[0].InnerCateegoryProductId+" "+inerCategorylist[0].discount)


    }


}
