package com.example.topaz.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Activities.OrderDetails
import com.example.topaz.Adapters.MyOrdersAdapter
import com.example.topaz.Adapters.NotifyAlertAdapter
import com.example.topaz.ApiModels.AlertApiModel
import com.example.topaz.ApiModels.ViewOrderApimodel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.NotifyAlertItemClickListner
import com.example.topaz.Interface.OrderItemClickListner
import com.example.topaz.Models.NotifyAlertModel
import com.example.topaz.Models.OrderItemListModel
import com.example.topaz.Models.OrderModels
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import kotlinx.android.synthetic.main.activity_account_information.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class AlertFragment : Fragment(), NotifyAlertItemClickListner, OrderItemClickListner {
    lateinit var progressBar: ProgressBar
    lateinit var alertRecycle: RecyclerView
    var offerAlertlist = java.util.ArrayList<NotifyAlertModel>()
    var custId = ""
    var orderListItem = java.util.ArrayList<OrderModels>()
    lateinit var alertContext: Context
    lateinit var noDataFoundText: TextView


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
        progressBar.visibility = View.VISIBLE

        alertContext=view.context
        onOfferApiCall(view)
      //  val  preferences = this.activity?.getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)


    }

    private fun initViews(view: View) {

        val sharedPreference =  this.activity?.getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference?.getString("customercode","").toString()

        progressBar = view.findViewById(R.id.app_progress_bar_2)
        alertRecycle = view.findViewById(R.id.notify_alert_recycler_view)
        noDataFoundText = view.findViewById(R.id.alertNoDataFoundText)
    }

    private fun onOfferApiCall(view: View) {
        try {
            val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            res.viewOders(custId).enqueue(object : Callback<List<ViewOrderApimodel>?> {
                override fun onResponse(
                    call: Call<List<ViewOrderApimodel>?>,
                    response: Response<List<ViewOrderApimodel>?>
                ) {

                    if (response.isSuccessful) {
                       // Log.d(TAG, "alert success: " + response.body())
                        progressBar.visibility = View.GONE


                        for (alertOffers in response.body()!!) {
                           Log.d(TAG, "alert success size: " + alertOffers.orderItems[0].brand)

                            if(alertOffers.orderstatus.status.contains("Quote")) {

                                if (alertOffers.orderItems.size > 0) {

                                    val odList = OrderModels(
                                        "",
                                        alertOffers.orderstatus.status,
                                        alertOffers.orderid,
                                        alertOffers.createdDate,
                                        alertOffers.orderItems.get(0).quantity,
                                        alertOffers.orderItems.get(0).thickness,
                                        alertOffers.orderItems.get(0).sqFeetPrice,
                                        alertOffers.orderItems.get(0).productRRR?.productTitle.toString(),
                                        alertOffers.paymentmode,
                                        alertOffers.customer.addressLine,
                                        amountWithoutTax = alertOffers.amountwithouttax,
                                        amountWithTax = alertOffers.amountwithtax,
                                        orderitemlist = alertOffers.orderItems

                                    )

                                    orderListItem.add(odList)
                                }
                            }




                          /*  var alertModel = NotifyAlertModel(
                                alertOffers.orderItems.get(0).product.categoryType.categoryimage.imagebyte,
                                alertOffers.orderItems.get(0).product.productTitle,
                                alertOffers.orderItems.get(0).thickness,
                                alertOffers.orderItems.get(0).price.toString(),
                                alertOffers.createdDate
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
                            alertRecycle.setHasFixedSize(true)*/
                        }


                        if(orderListItem.size==0){
                            noDataFoundText.visibility=View.VISIBLE
                        }
                        else{
                            noDataFoundText.visibility = View.GONE
                        }
                        //Setting adapter
                           val orderadapter= MyOrdersAdapter(orderListItem,this@AlertFragment,alertContext)
                        alertRecycle.layoutManager = LinearLayoutManager(alertContext)
                           alertRecycle.adapter = orderadapter
                           alertRecycle.setHasFixedSize(true)



                    } else {
                        Log.d(TAG, "alert Fail: " + response.body())
                    }

                }

                override fun onFailure(call: Call<List<ViewOrderApimodel>?>, t: Throwable) {
                    Log.d(TAG, "alert Failure: " + t.message)
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
                }
            })
        }
        catch (e:Exception){
            e.toString()
        }
    }


    override fun NotifyAlertItemClickListner(data: NotifyAlertModel) {
//do nothing
    }

    //Perform the action when Alert page is clicked
    override fun OrderItemClickListner(data: OrderModels) {

        try {
            var orderitemlist = ArrayList<OrderItemListModel>()
            for (orderitem in data.orderitemlist) {
                orderitemlist.add(
                    OrderItemListModel(
                        orderItemId = orderitem.orderItemId,
                        quantity = orderitem.quantity,
                        sqftprice = orderitem.sqftprice,
                        price = orderitem.price,
                        productTitle = orderitem.productRRR?.productTitle,
                        productImage = orderitem.productimage212.imagepath,
                        actualsqftprice = orderitem.productRRR?.actualSqFeet,
                        size = orderitem.size,
                    )
                )
            }

            var intent = Intent(activity, OrderDetails::class.java)
            intent.putExtra("QuotationStatus", data.QuotationStatus)
            intent.putExtra("quotationID", data.quotationID)
            intent.putExtra("quotationDate", data.quotationDate)
            intent.putExtra("quotationquantity", data.quotationquantity.toString())
            intent.putExtra("quotationThickness", data.quotationThickness)
            intent.putExtra("quotationSqftPrice", data.quotationSqftPrice.toString())
            intent.putExtra("quotationTitle", data.quotationTitle)
            intent.putExtra("quotationPaymentMode", data.quotationPaymentMode)
            intent.putExtra("quotationAddressLine", data.quotationAddressLine)
            intent.putExtra("amountwithtax", data.amountWithTax.toString())
            intent.putExtra("amountwithouttax", data.amountWithoutTax.toString())
            intent.putParcelableArrayListExtra("orderItemList", orderitemlist)

            startActivity(intent)
        }
        catch (e:Exception){
            e.toString()
        }




    }


}


