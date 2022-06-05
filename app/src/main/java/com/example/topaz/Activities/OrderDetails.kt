package com.example.topaz.Activities

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topaz.Adapters.OrderDetailsAdapter
import com.example.topaz.Interface.OrdDetPageItemclickListner
import com.example.topaz.Models.OrderModels
import com.example.topaz.databinding.ActivityOrderDetailsBinding

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

        binding.discountentry2.setText(Qdt)
        binding.deliverychargesentry2.setText(QiD)
        binding.totalamtentry2.setText(QsqftPr)
        binding.pod.setText(QpMode)
        binding.shipingname.setText(custName)
        binding.shipingad.setText(Qaddrs)



        activity = this
        setSupportActionBar(binding.orderDetailsToolbar)
        supportActionBar?.title = ""



        binding.detailsrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val orderdetailsAdapter = OrderDetailsAdapter(orderListItem2, this, this)
        binding.detailsrecycler.adapter = orderdetailsAdapter
    }

    override fun OrdDetPageItemclickListner(data: OrderModels) {
        //for future purpose
    }
}