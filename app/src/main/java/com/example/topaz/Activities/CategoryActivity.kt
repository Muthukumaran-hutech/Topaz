package com.example.topaz.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


      //  categorymainAdapter = CategoryAdapter()

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(this, 3)//Count depicts no of elements in row
        var categoryAdapter= CategoryAdapter(categoryInnerlist,this)
        binding.categoryRecyclerView.adapter =categoryAdapter
        binding.categoryRecyclerView.setHasFixedSize(true)

        categoryInnerlist.add(CategoriesModel(R.drawable.plywoodbiards,"PlywoodBoards",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.pvcboards,"Laminates",""))
        categoryInnerlist.add(CategoriesModel(R.drawable.face_veneers,"Face Veneers",""))


        binding.categorySearch.setOnClickListener{
            startActivity(Intent(activity,SearchActivity::class.java))
            finish()
        }

        binding.categoryCart.setOnClickListener{
            startActivity(Intent(activity,MyCart::class.java))
            finish()
        }

        binding.categoryNotification.setOnClickListener{
            Toast. makeText(applicationContext," Currently In Process", Toast. LENGTH_SHORT).show()

        }

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


}