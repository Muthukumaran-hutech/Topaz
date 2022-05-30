package com.example.topaz.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.NotifyAlertAdapter
import com.example.topaz.ApiModels.AlertApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.NotifyAlertItemClickListner
import com.example.topaz.Models.NotifyAlertModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AlertFragment : Fragment(), NotifyAlertItemClickListner {
    lateinit var progressBar: ProgressBar
    lateinit var alertRecycle: RecyclerView
    var offerAlertlist = java.util.ArrayList<NotifyAlertModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        onOfferApiCall(view)
        progressBar.visibility = View.VISIBLE
    }

    private fun initViews(view: View) {


        progressBar = view.findViewById(R.id.app_progress_bar_2)
        alertRecycle = view.findViewById(R.id.notify_alert_recycler_view)
    }

    private fun onOfferApiCall(view: View) {
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.alertNotify().enqueue(object : Callback<List<AlertApiModel>?> {
            override fun onResponse(
                call: Call<List<AlertApiModel>?>,
                response: Response<List<AlertApiModel>?>
            ) {

                if (response.isSuccessful) {
                    // Log.d(TAG, "alert success: "+response.body())
                    progressBar.visibility = View.GONE
                    for (alertOffers in response.body()!!) {
                        var alertModel = NotifyAlertModel(
                            alertOffers.customer.orderItems.get(0).product.categoryType.categoryimage.imagebyte,
                            alertOffers.customer.orderItems.get(0).product.productTitle,
                            alertOffers.customer.orderItems.get(0).price.toString(),
                            alertOffers.customer.orderItems.get(0).thickness,
                            alertOffers.customer.orderItems.get(0).product.categoryType.categoryimage.creationTime
                        )

                        offerAlertlist.add(alertModel)

                        alertRecycle.layoutManager =
                            LinearLayoutManager(context)//Count depicts no of elements in row
                        val alertAdapter =
                            NotifyAlertAdapter(
                                offerAlertlist,
                                this@AlertFragment,
                                this@AlertFragment
                            )
                        alertRecycle.adapter = alertAdapter
                        alertRecycle.setHasFixedSize(true)
                    }


                }

            }

            override fun onFailure(call: Call<List<AlertApiModel>?>, t: Throwable) {

            }
        })
    }


    override fun NotifyAlertItemClickListner(data: NotifyAlertModel) {

    }


}


