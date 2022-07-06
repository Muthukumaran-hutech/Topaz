package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.MyCartAdapter
import com.example.topaz.ApiModels.AddOrderApiModel
import com.example.topaz.Interface.IncreementDecreementItemClickListner
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Interface.MyCartItemClickListner
import com.example.topaz.Models.CartList
import com.example.topaz.Models.CartProductList
import com.example.topaz.Models.ProductQuotationsModel
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityMyCartBinding
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCart : AppCompatActivity(), MyCartItemClickListner,IncreementDecreementItemClickListner {

    private lateinit var binding: ActivityMyCartBinding
    lateinit var activity: Activity
    var cartData = java.util.ArrayList<CartProductList>()
    private lateinit var database: DatabaseReference
    private lateinit var database1: DatabaseReference
    var custId = ""
    private lateinit var cartAdapter:MyCartAdapter
    var productList = ArrayList<ProductQuotationsModel>()

    var payableamountwithouttax:Int=0
    var payableamountwithtax:Int = 0
    var taxamount=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appProgressBar2.visibility = View.VISIBLE

        binding.linearLayout3.visibility = View.GONE
        binding.chckbtn.visibility = View.GONE
        binding.cartRecycle.visibility = View.GONE

     /*val price =   binding.priceentry.setText(cartData.get(0).price)
      binding.discountentry.setText("")
      binding.deliverychargesentry.setText("")
       // val tot = price + discount + deliveryCharge
        binding.totalamtentry.text = price.toString()*/

        activity = this
        setSupportActionBar(binding.cartListToolbar)
        supportActionBar?.title = ""

        binding.backarrow2.setOnClickListener {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }


        binding.chckbtn.setOnClickListener {
           Log.d("--CART DATA--",prepareCartRequestObject().toString())
            createOrder()
        }



        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()
        retrieveDataFirebase()
        binding

        binding.cartRecycle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }


    private fun retrieveDataFirebase() {

        try {
            database = FirebaseDatabase.getInstance().getReference("MyCart")
            val query = database.child(custId.toString()).orderByChild("cartActive").equalTo(true)


            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap: DataSnapshot in snapshot.children) {
                            val mylistdata = snap.getValue(CartList::class.java) as CartList
                            // cartData.add(mylistdata)
                            Log.d("Mycartm", mylistdata.custId)
                            getCartItems(mylistdata.cartId)
                            //Write a function to get products from Cart
                            //fun_name(cart_id){    }

                        }


                    } else {
                        binding.appProgressBar2.visibility = View.GONE
                        binding.cartEmpty.visibility = View.VISIBLE
                        binding.linearLayout3.visibility= View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
        catch (e:Exception){
            e.toString()
        }
    }

    private fun getCartItems(cartId: String) {
        try {
            database1 = FirebaseDatabase.getInstance().getReference("MyCartProducts")
            val query =
                database1.child(cartId).child("Products").orderByChild("active").equalTo(true)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartData.clear()
                    if (snapshot.exists()) {
                        for (snap: DataSnapshot in snapshot.children) {
                            val mylistdata1 =
                                snap.getValue(CartProductList::class.java) as CartProductList
                            cartData.add(mylistdata1)
                            cartAdapter =
                                MyCartAdapter(cartData, this@MyCart, this@MyCart, this@MyCart)
                            binding.cartRecycle.adapter = cartAdapter
                            binding.cartRecycle.setHasFixedSize(true)
                            Log.d("productid", mylistdata1.product_id)
                            //Write a function to get products from Cart
                            //fun_name(cart_id){    }

                        }
                        if (cartData.size > 0) {
                            binding.appProgressBar2.visibility = View.GONE
                            binding.linearLayout3.visibility = View.VISIBLE
                            binding.chckbtn.visibility = View.VISIBLE
                            binding.cartRecycle.visibility = View.VISIBLE
                            binding.cartEmpty.visibility = View.GONE
                            initializePriceDetails()
                        }
                    } else {
                        binding.appProgressBar2.visibility = View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
        catch (e:Exception){
            e.toString()
        }

    }

    private fun initializePriceDetails() {
        try {
            //var price = 0;
            var totaldiscount = 0;
            var deliverycharges = 0
            //var actualprice=0;

            var totalamt=0
            for (cartlist in cartData) {
                if (cartlist.isActive) {
                    var quantity = 1
                    var discount=0
                    var price=0
                    var actualprice=0


                    //Get the size
                    //Get the quantity
                    //Get the discount for each product
                    //Sum it up
                    val size = Util.extractNumbersFromString(cartlist.size, "Size")
                    var actualsize = size[0].toDouble().toInt() * size[1].toDouble().toInt()

                    Log.d("Actual size", actualsize.toString())

                   /* if(cartlist.discount!=""){
                        val dis=Util.extractNumbersFromString(cartlist.discount,"Percentage")
                        discount=dis[0].toInt()
                    }
                    else{
                        discount=0
                    }
*/
                    if (!cartlist.quantity.isEmpty()) {
                        quantity = cartlist.quantity.toInt()
                    }

                    price = price + (cartlist.price.toDouble().toInt() * quantity * actualsize)//Collecting the price with adding Offer
                    actualprice =actualprice +(cartlist.actualPrice.toDouble().toInt() * quantity * actualsize)//Collecting the price without adding Offer

                    totalamt = totalamt + actualprice//

                   // totaldiscount = (totaldiscount + (cartlist.actualPrice.toDouble().toInt()* quantity*actualsize*discount)/100)
                    totaldiscount =totaldiscount +(actualprice - price)
                }

            }


            payableamountwithouttax = totalamt - totaldiscount//Without adding TAX

            val taxamt=(payableamountwithouttax * 18)/100
            taxamount=taxamt
            payableamountwithtax= taxamt + payableamountwithouttax//Amount with adding TAX(18%)



            binding.taxentryfield?.text= getString(R.string.Rs) + taxamt.toString()
            //With TAX
            binding.priceentry.text = getString(R.string.Rs) + totalamt.toString()
            binding.discountentry.text = "-" + getString(R.string.Rs) + totaldiscount.toString()
            binding.totalamtentry.text = getString(R.string.Rs) + payableamountwithtax.toString()


            Log.d("Total price", totalamt.toString())
            Log.d("Total discount", totaldiscount.toString())
            Log.d("Payable amount", payableamountwithtax.toString())
        }
        catch (e:Exception){
            e.toString()
        }
    }


    override fun MyCartItemClickListner(data: CartProductList, position: Int) {

        if (cartData.size == 1) {
            database.child(custId).child(data.cart_id).child("cartActive").setValue(false)
            cartData.remove(data)
            cartAdapter.notifyItemRemoved(position)
            database1.child(data.cart_id).child("Products").child(data.product_id).child("active").setValue(false)

            binding.linearLayout3.visibility = View.GONE
            binding.chckbtn.visibility = View.GONE
            binding.cartRecycle.visibility = View.GONE
            binding.cartEmpty.visibility = View.VISIBLE

        } else {
            //Write a function which changes the status of the product in MyCart
            cartData.remove(data)
            cartAdapter.notifyItemRemoved(position)
            database1.child(data.cart_id).child("Products").child(data.product_id).child("active").setValue(false)

            binding.linearLayout3.visibility = View.GONE
            binding.chckbtn.visibility = View.GONE
            binding.cartRecycle.visibility = View.GONE
            binding.cartEmpty.visibility = View.VISIBLE
        }

        Toast.makeText(this, "Removed from the MyCart", Toast.LENGTH_LONG).show()
    }

    override fun IncreementDecreementItemClickListner(data: CartProductList, quantity: Int) {
        Log.d(TAG, "IncreementDecreementItemClickListner: "+quantity.toString())
        updateQuantityToFirebase(data,quantity)
    }

    private fun updateQuantityToFirebase(cartData: CartProductList, quantity: Int) {
        database1.child(cartData.cart_id).child("Products").child(cartData.product_id).child("quantity").setValue(quantity.toString())

    }



    fun prepareCartRequestObject():JsonObject{
        val requestobject=JsonObject()
        try{


            //Main request object


            //Creating customer object
            var customerobject=JsonObject()
            customerobject.addProperty("customercode", custId)


            //Creating order object
            var ordertaxobject =JsonObject()
            ordertaxobject.addProperty("taxtype","GST")
            ordertaxobject.addProperty("taxpercentage","18%")
            ordertaxobject.addProperty("taxamount",taxamount)



            //Creating product object
            val product=JsonObject()
            product.addProperty("productid","")



                // Creating order discount

            val orderdiscountdetails=JsonObject()


            val orderdiscount=JsonObject()
            orderdiscount.addProperty("discountstatus","")
            orderdiscount.addProperty("exdiscount","")


            //Orderitem list

            val orderitemlist=JsonArray()

            for(cartitemlist in cartData){
                val orderdetailsObj = JsonObject()
                var size= listOf<String>()
                size=Util.extractNumbersFromString(cartitemlist.size,"Size")
                var productsize=size[0].toDouble().toInt() * size[1].toDouble().toInt()

                val totalprice= cartitemlist.price.toDouble().toInt() * cartitemlist.quantity.toInt()* productsize.toInt()


                orderdetailsObj.addProperty("quantity",cartitemlist.quantity.toInt())
                orderdetailsObj.addProperty("price",totalprice.toDouble())
                orderdetailsObj.addProperty("size",cartitemlist.size)
                orderdetailsObj.addProperty("thickness",cartitemlist.thickness)
                orderdetailsObj.addProperty("sqftprice",cartitemlist.price)
                product.addProperty("productid",cartitemlist.product_id)
                orderdetailsObj.add("product",Gson().toJsonTree(product))
                if(cartitemlist.discount!=""){
                    orderdiscountdetails.addProperty("discountid",cartitemlist.discount_id)
                }
                else{
                    orderdiscountdetails.addProperty("discountid","1".toInt())
                }
                orderdiscount.add("discount",Gson().toJsonTree(orderdiscountdetails))

                orderdetailsObj.add("orderDiscount",Gson().toJsonTree(orderdiscount))

                orderitemlist.add(orderdetailsObj)

            }

            //Main request object
            requestobject.add("customer", Gson().toJsonTree(customerobject))
            requestobject.addProperty("shippinghandling",.0)
            requestobject.addProperty("amountwithtax",payableamountwithtax.toDouble())
            requestobject.addProperty("amountwithouttax",payableamountwithouttax.toDouble())
            requestobject.add("orderTax",Gson().toJsonTree(ordertaxobject))
            requestobject.add("orderItems",Gson().toJsonTree(orderitemlist))

        }
        catch (e:Exception){
            e.toString()
        }

        return  requestobject

    }

    fun createOrder(){
        try{

            binding.appProgressBar2.visibility= View.VISIBLE
            var createorderres = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            createorderres.addNewOrder(prepareCartRequestObject()).enqueue(object : Callback<AddOrderApiModel?> {
                override fun onResponse(
                    call: Call<AddOrderApiModel?>,
                    response: Response<AddOrderApiModel?>
                ) {
                    binding.appProgressBar2.visibility = View.GONE
                    if(response.isSuccessful){

                        if(response.body()!=null){
                            val orderstatus=response.body()

                            if(orderstatus?.status==200){

                                Toast.makeText(this@MyCart,"Order placed successfully",Toast.LENGTH_LONG).show()
                                updateCartStatus()
                            }
                        }

                    }
                    else{
                        Toast.makeText(this@MyCart,"Failed to place the Order",Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<AddOrderApiModel?>, t: Throwable) {
                    Log.e("Add order failure",t.message.toString())
                    binding.appProgressBar2.visibility = View.GONE
                    Toast.makeText(this@MyCart,"Failed to place the Order",Toast.LENGTH_LONG).show()
                }
            })




        }
        catch (e:Exception){
            e.toString()
            binding.appProgressBar2.visibility = View.GONE
        }


    }

    fun updateCartStatus(){

        try{

            val cartref=FirebaseDatabase.getInstance().getReference("MyCart").child(custId)
            cartref.child(cartData.get(0).cart_id).child("cartActive").setValue(false)
            cartData.clear()
            cartAdapter.notifyDataSetChanged()
            startActivity(Intent(this@MyCart, MyOrders::class.java))


        }
        catch (e:Exception){
            e.toString()
        }


    }
}