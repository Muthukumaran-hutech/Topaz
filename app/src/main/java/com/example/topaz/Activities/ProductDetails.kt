package com.example.topaz.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.R
import com.example.topaz.databinding.ActivityCategoryBinding
import com.example.topaz.databinding.ActivityProductDetailsBinding

class ProductDetails : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    lateinit var activity: Activity
    val imageList = ArrayList<Int>()
    var slidemodellist = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        binding.prodScrollView.isFillViewport = true
        setSupportActionBar(binding.prodDetailsToolbar)
        supportActionBar?.title = ""

        binding.productBackArrow.setOnClickListener{
            startActivity(Intent(activity,InnerCategories::class.java))
            finish()
        }

        imageList.add(R.drawable.home_slider_banner)
        imageList.add(R.drawable.home_slider_banner_2)


        //-----------------Slide model list---------------------//
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))
        slidemodellist.add(SlideModel(imagePath = R.drawable.rectangle_2544))

        binding.productImageSlider.setImageList(slidemodellist, ScaleTypes.FIT)

        binding.whislistProdDetails.setOnClickListener {
            Toast.makeText(applicationContext, "Will be added to wishlist Shortly", Toast.LENGTH_SHORT)
                .show()
        }

        binding.shareProdDetails.setOnClickListener {
            Toast.makeText(applicationContext, "Will be placed with Google Playstore Link", Toast.LENGTH_SHORT)
                .show()
            //should pass the google playstorelink
            /*ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText("http://play.google.com/store/apps/details?id=" + activity.getPackageName())
                .startChooser();*/
        }
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
        inflater.inflate(R.menu.prod_page_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.search_bar->  startActivity(Intent(activity,SearchActivity::class.java))
            R.id.notification_bar->  Toast. makeText(applicationContext," Currently In Process",
                Toast. LENGTH_SHORT).show()
            R.id.my_cart->  startActivity(Intent(activity,MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

}