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
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.ApiModels.CategoryListApiModel
import com.example.topaz.ApiModels.UpdateUserApiModel
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityLoginBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catId = intent.getStringExtra("cat__iD").toString()

        activity = this
        setSupportActionBar(binding.catToolbar)
        supportActionBar?.title = ""

        binding.appProgressBar2.visibility = View.VISIBLE
        categoryApiCall()

        binding.home.setOnClickListener {
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

        }

        binding.categoryBackArrow.setOnClickListener {
            startActivity(Intent(activity, HomeScreen::class.java))

        }
    }

    private fun categoryApiCall() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)
        Log.d(
            TAG,
            "catId: " + catId
        )
        res.viewCategoryItems(catId).enqueue(object : Callback<List<CategoryListApiModel>?> {
            override fun onResponse(
                call: Call<List<CategoryListApiModel>?>,
                response: Response<List<CategoryListApiModel>?>
            ) {
                if (response.isSuccessful) {
                    binding.appProgressBar2.visibility = View.GONE
                    for (categorylist in response.body()!!) {

                        if (categorylist.active == true) {
                            var categoryModel = CategoriesModel(
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
                        "Something Went Wronng Please Try Again Later",
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
        inflater.inflate(R.menu.categories_page_menu, menu)
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

}