package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.ApiModels.CategoryListApiModel
import com.example.topaz.ApiModels.SubCategoryListApiModel
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_account_information.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity(), CategoryPageItemClickListner {


    private lateinit var binding: ActivityCategoryBinding

    //private lateinit var categorymainAdapter: CategoryAdapter
    lateinit var activity: Activity
    var categoryInnerlist = java.util.ArrayList<CategoriesModel>()
    var catId = ""
    var categoryName ="";
    lateinit var bottomnav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catId = intent.getStringExtra("cat__iD").toString()
        categoryName = intent.getStringExtra("category_name") as String

        activity = this
        setSupportActionBar(binding.catToolbar)
        supportActionBar?.title = ""

        binding.appProgressBar2.visibility = View.VISIBLE
        categoryApiCall()


       /* binding.home?.setOnClickListener {
            startActivity(Intent(activity, HomeScreen::class.java))


        }

        binding.categories.setOnClickListener {
            //Do Nothing
            // Toast. makeText(applicationContext,"You Are Currently In Category Page", Toast. LENGTH_SHORT).show()
        }

        binding.account.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))

        }

        binding.fav.setOnClickListener {
            startActivity(Intent(activity, MyWishlist::class.java))

        }*/

       bottomnav=binding.catBottomNavLayout!!.bottomNavItem
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


        binding.categoryBackArrow.setOnClickListener {
            finish()
            //startActivity(Intent(activity, HomeScreen::class.java))

        }


    }

    private fun categoryApiCall() {
        val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)
        Log.d(
            TAG,
            "catId: " + catId
        )

        res.getSubcategoryList(categoryName).enqueue(object : Callback<List<SubCategoryListApiModel>?> {
            override fun onResponse(
                call: Call<List<SubCategoryListApiModel>?>,
                response: Response<List<SubCategoryListApiModel>?>
            ) {

                  if(response.isSuccessful){
                      binding.appProgressBar2.visibility = View.GONE
                      for(categorylist in response.body()!!){

                          val categoryModel = CategoriesModel(
                              "",
                              categorylist.subcategoryname,
                              "",
                              categorylist.subid

                          )
                          categoryInnerlist.add(categoryModel)
                          binding.categoryRecyclerView.layoutManager = GridLayoutManager(
                              this@CategoryActivity,
                              3
                          )//Count depicts no of elements in row
                          val categoryAdapter = CategoryAdapter(
                              categoryInnerlist,
                              this@CategoryActivity,
                              this@CategoryActivity
                          )
                          binding.categoryRecyclerView.adapter = categoryAdapter
                          binding.categoryRecyclerView.setHasFixedSize(true)



                      }
                  }
                  else {
                      binding.appProgressBar2.visibility = View.VISIBLE
                      Toast.makeText(
                          applicationContext,
                          "Something Went Wrong Please Try Again Later",
                          Toast.LENGTH_LONG
                      ).show()

                  }


            }

            override fun onFailure(call: Call<List<SubCategoryListApiModel>?>, t: Throwable) {
                binding.appProgressBar2.visibility = View.GONE
                Toast.makeText(
                    applicationContext,
                    "Something Went Wrong Please Try Again Later",
                    Toast.LENGTH_LONG
                ).show()

            }
        })
       /* res.viewCategoryItems(catId).enqueue(object : Callback<List<CategoryListApiModel>?> {
            override fun onResponse(
                call: Call<List<CategoryListApiModel>?>,
                response: Response<List<CategoryListApiModel>?>
            ) {
                if (response.isSuccessful) {
                    binding.appProgressBar2.visibility = View.GONE
                    for (categorylist in response.body()!!) {

                        if (categorylist.active == true) {
                            val categoryModel = CategoriesModel(
                                categorylist.categoryimage.imagebyte,
                                categorylist.categoryName,
                                categorylist.categoryid,
                                categorylist.subcategory1.get(0).subid

                            )
                            categoryInnerlist.add(categoryModel)
                        }

                    }
                    binding.categoryRecyclerView.layoutManager = GridLayoutManager(
                        this@CategoryActivity,
                        3
                    )//Count depicts no of elements in row
                    var categoryAdapter = CategoryAdapter(
                        categoryInnerlist,
                        this@CategoryActivity,
                        this@CategoryActivity
                    )
                    binding.categoryRecyclerView.adapter = categoryAdapter
                    binding.categoryRecyclerView.setHasFixedSize(true)

                    Log.d(
                        TAG,
                        "onResponse: " + response.body()?.get(0)?.tags?.get(0)?.tags.toString()
                    )
                } else {
                    binding.appProgressBar2.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wrong Please Try Again Later",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.d(
                        TAG,
                        "OnFailure: " + response.body()?.get(0)?.tags?.get(0)?.tags.toString()
                    )
                }


            }

            override fun onFailure(call: Call<List<CategoryListApiModel>?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Something Went Wronng Please Try Again Later",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        )
*/

    }

    override fun CategoryPageItemClickListner(categories: CategoriesModel) {
        var intent = Intent(activity, InnerCategories::class.java)
        intent.putExtra("cat_id", categories.CateegorySubId)
        startActivity(intent)

    }


    /* override fun onBackPressed() {
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
     }*/

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
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            /*R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))*/
        }
        return super.onOptionsItemSelected(item)

    }

}