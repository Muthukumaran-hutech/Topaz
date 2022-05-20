package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.ProductDetailsModel
import com.example.topaz.Models.ProductQuotationsModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityProductQuotationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductQuotation : AppCompatActivity() {


    private lateinit var binding: ActivityProductQuotationBinding
    private lateinit var productAdapter: ProductQuotationAdapter
    lateinit var activity: Activity
    var productList = ArrayList<ProductQuotationsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductQuotationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        setSupportActionBar(binding.quotationToolbar)
        supportActionBar?.title = ""
       // onApiCallProductDetails()
        productAdapter = ProductQuotationAdapter()

        binding.productQuotqtionRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.productQuotqtionRecycler.adapter = productAdapter


        binding.productQuotationBackArrow.setOnClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
    }

   /* private fun onApiCallProductDetails() {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)


        res.viewProduct().enqueue(object : Callback<List<ProductDetailsListApiModel>?> {
            override fun onResponse(
                call: Call<List<ProductDetailsListApiModel>?>,
                response: Response<List<ProductDetailsListApiModel>?>
            ) {
                if (response.isSuccessful) {
               //     binding.appProgressBar3.visibility = View.GONE

                    for(productlist in response.body()!!){
                        var product= ProductQuotationsModel(
                            "",
                            productlist.productTitle,
                            productlist.price.toString(),
                            productlist.size,
                            productlist.thickness,
                            productlist.brand,
                            productlist.woodType,
                            productlist.discription
                        )
                        productList.add(product)
                    }
                    //set detAILS...........
                    binding.woodMaterialName.text=productList.get(0).ProductTitle
                    binding.qutyentry.text=productList.get(0)
                    binding.priceentry.text=productList.get(0).ProductThickness
                    binding.productSpecificationBrand.text=productList.get(0).ProductBrand
                    binding.productSpecificationWoodType.text=productList.get(0).ProductWoodType
                    binding.productSpecificationDesc.text=productList.get(0).ProductDescription
                    Log.d(ContentValues.TAG, "onResponseProduct: "+ response.body()?.get(0)?.discription)
                }else{
                    Log.d(ContentValues.TAG, "OnFailure: "+ response.body()?.get(0)?.discription)
                }
            }

            override fun onFailure(call: Call<List<ProductDetailsListApiModel>?>, t: Throwable) {
             //   binding.appProgressBar3.visibility = View.VISIBLE
                Toast. makeText(applicationContext," Something Went Wrong Please Try Again Later",
                    Toast. LENGTH_LONG).show()

            }

        })
    }*/


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.product_quotation_menu, menu)
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