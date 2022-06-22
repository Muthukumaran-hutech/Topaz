package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.OrderDetailsAdapter
import com.example.topaz.ApiModels.ItmOrdr
import com.example.topaz.Interface.OrdDetPageItemclickListner
import com.example.topaz.Models.OrderItemListModel
import com.example.topaz.Models.OrderModels
import com.example.topaz.R
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityOrderDetailsBinding
import java.util.ArrayList

class OrderDetails : AppCompatActivity(), OrdDetPageItemclickListner {

    private lateinit var binding: ActivityOrderDetailsBinding
    lateinit var activity: Activity
    var custName = ""
    var orderListItem2 = java.util.ArrayList<OrderModels>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custName = sharedPreference.getString("customerName", "").toString()

        val Qstat = intent.getStringExtra("QuotationStatus")
        val QiD = intent.getStringExtra("quotationID")
        val Qdt = intent.getStringExtra("quotationDate")
        val Qquantity = intent.getStringExtra("quotationquantity")
        val Qthickness = intent.getStringExtra("quotationThickness")
        val QsqftPr = intent.getStringExtra("quotationSqftPrice")
        val Qtitle = intent.getStringExtra("quotationTitle")
        val QpMode = intent.getStringExtra("quotationPaymentMode")
        val Qaddrs = intent.getStringExtra("quotationAddressLine")
        val amountwithouttax=intent.getStringExtra("amountwithouttax")
        val amountwithtax= intent.getStringExtra("amountwithtax")
        var orderitemlist= intent.getParcelableArrayListExtra<OrderItemListModel>("orderItemList")

        for(orderitems in orderitemlist!!){
            orderListItem2.add(OrderModels(
                quotationTitle = orderitems.productTitle,
                quotationSqftPrice = orderitems.sqftprice!!,
                image = orderitems.productImage!!,
                actualSqFeetPrice = orderitems.actualsqftprice,
                size = orderitems.size,
                quantity = orderitems.quantity
                ))
        }

        var totalprice=0;
        var totaldiscount=0
        for(orderlist in orderListItem2 ){
            try {
                var actualprice = 0
                var discountedprice = 0
                val sizedata = Util.extractNumbersFromString(orderlist.size!!, "Size")
                discountedprice = orderlist.quotationSqftPrice.toDouble().toInt() * (sizedata[0].toDouble()
                    .toInt() * sizedata[1].toDouble().toInt()) * orderlist.quantity!!

                actualprice = orderlist.actualSqFeetPrice!!.toDouble().toInt() * (sizedata[0].toDouble()
                    .toInt() * sizedata[1].toDouble().toInt()) * orderlist.quantity!!
                //Get the total

                totalprice = totalprice + actualprice
                totaldiscount = totaldiscount + (actualprice - discountedprice)
            }
            catch (e:Exception){
                e.toString()
            }

        }


        Log.d("-Total price-",totalprice.toString())

        Log.d("-Total discount-", totaldiscount.toString())

        //orderListItem2.add(OrderModels(Qtitle.toString(),Qthickness.toString(),QsqftPr.toString()))
     try {
         binding.discountentry2.setText(Qdt)
         binding.deliverychargesentry2.setText(QiD)
         binding.pod.setText(QpMode)
         binding.shipingname.setText(custName)
         binding.shipingad.setText(Qaddrs)

         binding.discountentry3.setText(getString(R.string.Rs)+totalprice.toString())
         binding.deliverychargesentry3.setText(getString(R.string.Rs)+totaldiscount.toString())
         val taxamnt= amountwithtax?.toDouble()?.toInt()!! - amountwithouttax?.toDouble()?.toInt()!!
         binding.taxentry.setText(
             (getString(R.string.Rs)+taxamnt.toString())
         )
         binding.ttlamt.setText(getString(R.string.Rs)+amountwithtax)
         binding.totalamtentry2.setText(getString(R.string.Rs)+amountwithtax)

     }
     catch (e:Exception){
         e.toString()
     }



        activity = this
        setSupportActionBar(binding.orderDetailsToolbar)
        supportActionBar?.title = ""




        binding.detailsrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val orderdetailsAdapter = OrderDetailsAdapter(orderListItem2, this, this)
        binding.detailsrecycler.adapter = orderdetailsAdapter


        binding.categoryBackArrow.setOnClickListener {
            finish()
        }
    }

    override fun OrdDetPageItemclickListner(data: OrderModels) {
        //for future purpose
    }
}