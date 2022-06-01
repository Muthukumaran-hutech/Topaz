package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MyOrdersAdapter
import com.example.topaz.ApiModels.ViewOrderApimodel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.OrderItemClickListner
import com.example.topaz.Models.OrderModels
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.databinding.ActivityMyOrdersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrders : AppCompatActivity(), OrderItemClickListner {

    private lateinit var binding: ActivityMyOrdersBinding
    lateinit var activity: Activity


    var custId = ""
    var orderListItem = java.util.ArrayList<OrderModels>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        activity = this
        setSupportActionBar(binding.orderToolbar)
        supportActionBar?.title = ""
        onOrderApiCall(custId)




        binding.ordersrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.categoryBackArrow.setOnClickListener {
            startActivity(Intent(activity, MyAccount::class.java))
            finish()
        }
    }

    private fun onOrderApiCall(custId: String) {

        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewOders(custId).enqueue(object : Callback<List<ViewOrderApimodel>?> {
            override fun onResponse(
                call: Call<List<ViewOrderApimodel>?>,
                response: Response<List<ViewOrderApimodel>?>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Onorder success: " + response.body())
                    for (orderList in response.body()!!) {
                        var odList = OrderModels(
                            orderList.orderstatus.status,
                            orderList.orderid,
                            orderList.createdDate

                        )
                     //   Log.d(TAG, "qty: "+orderList.orderItems[0].quantity.toString())
                        orderListItem.add(odList)
                    }

                    var ordersAdapter = MyOrdersAdapter(orderListItem, this@MyOrders, this@MyOrders)
                    binding.ordersrecycler.adapter = ordersAdapter
                } else {
                    Log.d(TAG, "Onorder Fail: " + response.body())
                }
            }

            override fun onFailure(call: Call<List<ViewOrderApimodel>?>, t: Throwable) {
                Log.d(TAG, "Onorder Failure: " + t.message)
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.orders_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

    override fun OrderItemClickListner(data: OrderModels) {
//for future use
    }
}