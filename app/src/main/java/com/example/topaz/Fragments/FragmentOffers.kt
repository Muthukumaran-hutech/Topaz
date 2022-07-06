package com.example.topaz.Fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.CategoryAdapter
import com.example.topaz.Adapters.NotifyOfferAdapter
import com.example.topaz.ApiModels.OffersListApiModel
import com.example.topaz.ApiModels.ProductDetailsListApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.NotifyOfferItemClickListner
import com.example.topaz.Models.CategoriesModel
import com.example.topaz.Models.NotifyOfferModels
import com.example.topaz.Models.ProductDetailsModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class FragmentOffers : Fragment(), NotifyOfferItemClickListner {
    lateinit var progressBar: ProgressBar
    lateinit var offerrecycle: RecyclerView
    var offerInnerlist = java.util.ArrayList<NotifyOfferModels>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
         onOfferApiCall(view)
        progressBar.visibility = View.VISIBLE
    }

    private fun initViews(view: View) {


        progressBar = view.findViewById(R.id.app_progress_bar_2)
        offerrecycle = view.findViewById(R.id.notify_offer_recycler_view)
    }

    private fun onOfferApiCall(view:View) {
        try {
            var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)


            res.viewOffers().enqueue(object : Callback<List<OffersListApiModel>?> {
                override fun onResponse(
                    call: Call<List<OffersListApiModel>?>,
                    response: Response<List<OffersListApiModel>?>
                ) {
                    if (response.isSuccessful) {
                        progressBar.visibility = View.GONE
                        for (notifyOffers in response.body()!!) {
                            var notifyOfferModel = NotifyOfferModels(
                                notifyOffers.offerImage?.imagebyte,
                                notifyOffers.title,
                                notifyOffers.description

                            )
                            offerInnerlist.add(notifyOfferModel)
                            offerrecycle.layoutManager =
                                LinearLayoutManager(context)//Count depicts no of elements in row
                            var notifyadapter =
                                NotifyOfferAdapter(offerInnerlist, this@FragmentOffers)
                            offerrecycle.adapter = notifyadapter
                            offerrecycle.setHasFixedSize(true)
                        }
                        Log.d(TAG, "onResponse Success: " + response.body()?.get(0)?.description)


                    }
                }

                override fun onFailure(call: Call<List<OffersListApiModel>?>, t: Throwable) {
                    Log.d(TAG, "onResponse Failure: " + t.message)

                }
            })/*object : Callback<List<OffersListApiModel>?> {
            override fun onResponse(
                call: Call<List<ProductDetailsListApiModel>?>,
                response: Response<List<ProductDetailsListApiModel>?>
            ) {
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    for(productlist in response.body()!!){
                        var product= ProductDetailsModel(
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
                    binding.productSpecificationSize.text=productList.get(0).ProductSize
                    binding.productSpecificationThickness.text=productList.get(0).ProductThickness
                    binding.productSpecificationBrand.text=productList.get(0).ProductBrand
                    binding.productSpecificationWoodType.text=productList.get(0).ProductWoodType
                    binding.productSpecificationDesc.text=productList.get(0).ProductDescription
                    Log.d(ContentValues.TAG, "onResponseProduct: "+ response.body()?.get(0)?.discription)
                }else{
                    Log.d(ContentValues.TAG, "OnFailure: "+ response.body()?.get(0)?.discription)
                }
            }

            override fun onFailure(call: Call<List<ProductDetailsListApiModel>?>, t: Throwable) {
                binding.appProgressBar3.visibility = View.VISIBLE
                Toast. makeText(applicationContext," Something Went Wrong Please Try Again Later",
                    Toast. LENGTH_LONG).show()

            }

        })*/
        }
        catch (e:Exception){
            e.toString()
        }

    }
    override fun NotifyOfferItemClickListner(data: NotifyOfferModels) {
        //for future implementations
    }


}