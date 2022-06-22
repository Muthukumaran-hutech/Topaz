package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MyOrdersAdapter
import com.example.topaz.ApiModels.ViewOrderApimodel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.OrderItemClickListner
import com.example.topaz.Models.OrderItemListModel
import com.example.topaz.Models.OrderModels
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityMyOrdersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

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

        binding.orderProgress.visibility=View.VISIBLE
        var res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
            .create(JsonPlaceholder::class.java)

        res.viewOders(custId).enqueue(object : Callback<List<ViewOrderApimodel>?> {
            override fun onResponse(
                call: Call<List<ViewOrderApimodel>?>,
                response: Response<List<ViewOrderApimodel>?>
            ) {
                binding.orderProgress.visibility=View.GONE
                if (response.isSuccessful) {
                    Log.d(TAG, "Onorder success: " + response.body())
                    for (orderList in response.body()!!) {
                        /* var odList = OrderModels(
                            orderList.orderstatus.status,
                            orderList.orderid,
                            orderList.createdDate
                        )
                        orderListItem.add(odList)
                    }*/


//                        Log.d(TAG, "Onorder success2: " + orderList.orderItems.get(0).product.productTitle)
                        var odList = OrderModels(
                            "",
                            orderList.orderstatus.status,
                            orderList.orderid,
                            orderList.createdDate,
                            orderList.orderItems.get(0).quantity,
                            orderList.orderItems.get(0).thickness,
                            orderList.orderItems.get(0).sqFeetPrice,
                            orderList.orderItems.get(0).productRRR?.productTitle.toString(),
                            orderList.paymentmode,
                            orderList.customer.addressLine,
                            amountWithoutTax = orderList.amountwithouttax,
                            amountWithTax = orderList.amountwithtax,
                            orderitemlist = orderList.orderItems


                        )
                        //   Log.d(TAG, "qty: "+orderList.orderItems[0].quantity.toString())

                        orderListItem.add(odList)

                        if(orderListItem.size==0){
                            binding.orderEmptyText.visibility=View.VISIBLE
                        }
                        else{
                            binding.orderEmptyText.visibility=View.GONE
                        }


                        Log.d(TAG, "Onorder success2: " + orderList.orderItems.size)
                    }

                   /* var ordersAdapter = MyOrdersAdapter(orderListItem, this@MyOrders, this@MyOrders)*/
                    val ordersAdapter = MyOrdersAdapter(orderListItem, this@MyOrders, this@MyOrders)
                    binding.ordersrecycler.adapter = ordersAdapter
                } else {
                    Log.d(TAG, "Onorder Fail: " + response.body())
                    binding.orderProgress.visibility=View.GONE
                }
            }

            override fun onFailure(call: Call<List<ViewOrderApimodel>?>, t: Throwable) {
                Log.d(TAG, "Onorder Failure: " + t.message)
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.homepagemenu, menu)

        var menuitem=menu.findItem(R.id.my_cart)
        var actionview=menuitem.actionView
        var cartcount=actionview.findViewById<TextView>(R.id.cart_count)//Getting the textview reference from action layout defined for the menu item
        var cart=actionview.findViewById<ImageView>(R.id.cart_icon)

        var quoteitem = menu.findItem(R.id.quotationStatus)
        var quoteactionview= quoteitem.actionView
        var quotecount = quoteactionview.findViewById<TextView>(R.id.quotation_count)
        var quotationview = quoteactionview.findViewById<ImageView>(R.id.quotationstatus_icon)
        setupCartCount(cartcount, cart)
        setUpQuoteListCount(quotecount,quotationview)
        return true
    }

    private fun setupCartCount(cartcount: TextView?, cart: ImageView?) {
        cart?.setOnClickListener {
            startActivity(Intent(activity, MyCart::class.java))
        }


        Util.getCartCount(context = this,object : Util.CartCountListener {//Gets the cart count
        override fun getCartCount(cartsize: Int) {
            //Get the cart count entries
            Log.d("--Cart count--",cartsize.toString())
            if(cartsize==0) {
                cartcount?.visibility= View.GONE
            }else {
                cartcount?.visibility= View.VISIBLE
                cartcount?.text = cartsize.toString()
            }

        }
        })

    }


    private fun setUpQuoteListCount(quotecount: TextView?, quotationview: ImageView?) {
        quotationview?.setOnClickListener {
            startActivity(Intent(this,ProductQuotation::class.java))
        }


        Util.getQuoteListEntry(this,object : Util.QuotationCountListener {
            override fun getQuotationCount(quotationsize: Int) {

                if(quotationsize==0){
                    quotecount?.visibility= View.GONE
                }
                else{
                    quotecount?.visibility = View.VISIBLE
                    quotecount?.text  = quotationsize.toString()

                }

            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

    override fun OrderItemClickListner(data: OrderModels) {

        var orderitemlist=ArrayList<OrderItemListModel>()
        for( orderitem in data.orderitemlist){
            orderitemlist.add(
                OrderItemListModel(
                orderItemId = orderitem.orderItemId,
                quantity = orderitem.quantity,
                sqftprice =   orderitem.sqftprice,
                price = orderitem.price,
                productTitle = orderitem.productRRR?.productTitle,
                productImage = orderitem.productimage212.imagepath,
                actualsqftprice = orderitem.productRRR?.actualSqFeet,
                size = orderitem.size,
            ))
        }


        var intent =Intent(activity, OrderDetails::class.java)
        intent.putExtra("QuotationStatus",data.QuotationStatus)
        intent.putExtra("quotationID",data.quotationID)
        intent.putExtra("quotationDate",data.quotationDate)
        intent.putExtra("quotationquantity",data.quotationquantity.toString())
        intent.putExtra("quotationThickness",data.quotationThickness)
        intent.putExtra("quotationSqftPrice",data.quotationSqftPrice.toString())
        intent.putExtra("quotationTitle",data.quotationTitle)
        intent.putExtra("quotationPaymentMode",data.quotationPaymentMode)
        intent.putExtra("quotationAddressLine",data.quotationAddressLine)
        intent.putExtra("amountwithtax",data.amountWithTax.toString())
        intent.putExtra("amountwithouttax",data.amountWithoutTax.toString())
        intent.putParcelableArrayListExtra("orderItemList",orderitemlist)

        startActivity(intent)
        //Log.d(TAG, "OrderItemClickListner: "+ data.quotationThickness)
//for future use
    }
}