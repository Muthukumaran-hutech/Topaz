package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
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
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.InnerCategoryAdapter
import com.example.topaz.Adapters.MywishlistAdapter
import com.example.topaz.ApiModels.CategoryListApiModel
import com.example.topaz.ApiModels.SubCategoryApiModel
import com.example.topaz.Interface.InnerCategoryItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.InnerCategoryModelList
import com.example.topaz.Models.ProductDetailsModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityInnerCategoriesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInnerCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        val item = intent.getStringExtra("cat_id")
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








    }

    private fun onApiCallInnerCategory() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.subCategory(catid).enqueue(object : Callback<List<SubCategoryApiModel>?> {
            override fun onResponse(
                call: Call<List<SubCategoryApiModel>?>,
                response: Response<List<SubCategoryApiModel>?>
            ) {

                if(response.isSuccessful){
                    for(innercategorylist in response.body()!!){
                        var innerCategoryModelList = InnerCategoryModelList(
                            innercategorylist.Productimage1.imagebyte,
                            innercategorylist.productTitle,
                            innercategorylist.price.toString(),
                            innercategorylist.thickness,
                            innercategorylist.subcategory.subid.toString(),
                            innercategorylist.productid.toString()

                        )
                        inerCategorylist.add(innerCategoryModelList)

                    }
                    val innercategoryAdapter = InnerCategoryAdapter(inerCategorylist,this@InnerCategories,this@InnerCategories)
                    binding.innerrecycler.adapter = innercategoryAdapter
                    binding.innerrecycler.setHasFixedSize(true)
                    //Toast. makeText(applicationContext,"Something Went Wronng Please Try Again Later", Toast.LENGTH_LONG).show()

                    Log.d(
                        TAG,
                        "On Inner: " + response.body()
                    )
                }else{
                    Toast. makeText(applicationContext,"NO DATA TO DISPLAY", Toast.LENGTH_LONG).show()

                    Log.d(
                        TAG,
                        "OnFail inner: " + response.body()
                    )
                }
            }

            override fun onFailure(call: Call<List<SubCategoryApiModel>?>, t: Throwable) {
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
        }
        return super.onOptionsItemSelected(item)

    }



}
