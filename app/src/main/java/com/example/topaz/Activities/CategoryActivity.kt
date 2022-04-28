package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Interface.CategoryPageItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.HomeCategoryModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityLoginBinding

class CategoryActivity : AppCompatActivity(), CategoryPageItemClickListner {


    private lateinit var binding: ActivityCategoryBinding
    //private lateinit var categorymainAdapter: CategoryAdapter
    lateinit var activity: Activity
    var categoryInnerlist= java.util.ArrayList<CategoriesModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        setSupportActionBar(binding.catToolbar)
        supportActionBar?.title = ""


      //  categorymainAdapter = CategoryAdapter()

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(this, 3)//Count depicts no of elements in row
        var categoryAdapter= CategoryAdapter(categoryInnerlist,this)
        binding.categoryRecyclerView.adapter =categoryAdapter
        binding.categoryRecyclerView.setHasFixedSize(true)

        categoryInnerlist.add(CategoriesModel(R.drawable.plywoodbiards,"PlywoodBoards",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.laminates,"Laminates",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.veneers,"Veneers",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.faceveneers,"Face Veneers",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.mdf,"MDF",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.doors,"Doors",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.pvcboards,"PVC Boards",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.furntureboards,"Furniture Boards",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.exteria,"Exteria",""))




        binding.home.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()

        }

        binding.categories.setOnClickListener{
            //Do Nothing
           // Toast. makeText(applicationContext,"You Are Currently In Category Page", Toast. LENGTH_SHORT).show()
        }

        binding.account.setOnClickListener{
            startActivity(Intent(activity,MyAccount::class.java))
            finish()
        }

        binding.fav.setOnClickListener{
            startActivity(Intent(activity,MyWishlist::class.java))
            finish()
        }

        binding.categoryBackArrow.setOnClickListener{
            startActivity(Intent(activity,HomeScreen::class.java))
            finish()
        }
    }

    override fun CategoryPageItemClickListner(categories: CategoriesModel) {
        startActivity(Intent(activity, InnerCategories::class.java))
        finish()
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
                dismissDialog(0)
                //  binding.phoneContainer.helperText = getString(R.id.Required)
            }
            .show()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.categories_page_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.search_bar->  startActivity(Intent(activity,SearchActivity::class.java))
            R.id.notification_bar->  Toast. makeText(applicationContext," Currently In Process",Toast. LENGTH_SHORT).show()
            R.id.my_cart->  startActivity(Intent(activity,MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

}