package com.example.topaz.Activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topaz.Adapters.FeetAdapter
import com.example.topaz.Adapters.ProductQuotationAdapter
import com.example.topaz.Adapters.QuotationListAdapter
import com.example.topaz.ApiModels.QuotationApiModel
import com.example.topaz.Interface.JsonPlaceholder
import com.example.topaz.Models.*
import com.example.topaz.R
import com.example.topaz.RetrofitApiInstance.UpdateAccountInfoInstance
import com.example.topaz.Utility.Util
import com.example.topaz.databinding.ActivityProductQuotationBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductQuotation : AppCompatActivity() {


    private lateinit var binding: ActivityProductQuotationBinding
    private lateinit var productAdapter: ProductQuotationAdapter
    private lateinit var feetAdapter: FeetAdapter
    lateinit var activity: Activity
    var productList = ArrayList<ProductQuotationsModel>()
    var thickness_list = ArrayList<ThicknessModel>()
    var feet_list = ArrayList<FeetModel>()
    var custId = ""
    var id=0
    var quotationlist=ArrayList<QuatationListModel>()
    var orderDetails = ArrayList<JsonElement>()
    var quotationId=""
    //lateinit var btnShowBottomSheet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductQuotationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        setSupportActionBar(binding.quotationToolbar)
        supportActionBar?.title = ""

        // onApiCallProductDetails()
        productAdapter = ProductQuotationAdapter(thickness_list)
        feetAdapter = FeetAdapter(feet_list)

      /*  val item = intent.getParcelableExtra<ProductDetailsModel>("extra_item")
        id = item?.ProductId!!
        var img = item.productImage
        var title = item.ProductTitle
        var price = item.ProductPrice
        var size = item.ProductSize
        var thickness = item.ProductThickness
        var brand = item.ProductBrand
        var woodType = item.ProductWoodType

        //Log.d(TAG, "onCreate: "+title)
        binding.priceentry.setText(price)
        binding.woodMaterialName.setText(title)
        binding.thic1.setText(thickness)
        binding.priceentry.isEnabled = false
        binding.totalvalue.isEnabled = false*/

        val sharedPreference = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE)
        custId = sharedPreference.getString("customercode", "").toString()

        //var quantity = binding.qutyentry.text.toString()

        binding.qutyentry.setOnClickListener {
            val dialog = BottomSheetDialog(this)

            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

            val btnConfirm = view.findViewById<Button>(R.id.idBtnconfirm)
            val quantitys = view.findViewById<EditText>(R.id.count_text)

            btnConfirm.setOnClickListener {
                try {
                    binding.qutyentry.setText(quantitys.text.toString())
                    dialog.dismiss()
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
            dialog.setCancelable(false)

            dialog.setContentView(view)
            dialog.show()
        }

        binding.quotationbtn.setOnClickListener {
            Log.d("--QT JSON--",customerDetails(updateCustomerInfo()).toString())
            if(quotationlist.size >0) {
                onApiCall()
            }
        }

       /* binding.qutyentry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {

                    if (s.toString().isNotEmpty()) {

                        val size1 = Util.extractNumbersFromString(size,"Size")
                        val length = size1[0].toDouble().toInt()
                        val breadth = size1[1].toDouble().toInt()
                        Log.d("Length&Breadth",size1.size.toString())
                        val totalValue =
                            s.toString().toInt() * price.toDouble()!!.toInt() * (length * breadth)
                        if (totalValue <= 0) {
                            binding.totalvalue.setText(price)
                        } else {
                            binding.totalvalue.setText(totalValue.toString())
                        }
                    } else {
                        //do nothing
                        binding.totalvalue.setText(price)
                    }
                }
                catch (e:java.lang.Exception){
                    e.printStackTrace()
                }


            }
        })
*/
        //thickness adapter
       /* thickness_list.add(ThicknessModel(thickness.toString()))
        productAdapter = ProductQuotationAdapter(thickness_list)
        binding.productQuotqtionRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.productQuotqtionRecycler.adapter = productAdapter
*/

        //Feet Adapter

       /* feet_list.add(FeetModel(size.toString()))
        feetAdapter = FeetAdapter(feet_list)
        binding.feetRecyclyer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.feetRecyclyer.adapter = feetAdapter*/


        binding.productQuotationBackArrow.setOnClickListener {
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }


        binding.addMoreProduct.setOnClickListener {//Moving to category page
            var intent=Intent(this, CategoryActivity::class.java)
            intent.putExtra("from","Bottomnav")
            startActivity(intent)

        }
       /* var quotationadapter=QuotationListAdapter(qlist,this)
        binding.quotationList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.quotationList.adapter=quotationadapter*/

        fetchQuotationList()
    }

    private fun onApiCall() {

        try {
            binding.quotationProgress.visibility = View.VISIBLE

            val res = UpdateAccountInfoInstance.getUpdateAccountInfoInstance()
                .create(JsonPlaceholder::class.java)

            res.askQotation(customerDetails(updateCustomerInfo()))
                .enqueue(object : Callback<QuotationApiModel?> {
                    override fun onResponse(
                        call: Call<QuotationApiModel?>,
                        response: Response<QuotationApiModel?>
                    ) {
                        binding.quotationProgress.visibility = View.GONE
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Quotation Sent  Sucessfully",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            Log.d(TAG, "onResponse quotation sucess: " + response.body().toString())

                            updateQuotationStatus()

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Quotation Sent  Failed",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            Log.d(TAG, "onResponse quotation fail: " + response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<QuotationApiModel?>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Something went wrong ",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Log.d(TAG, "onResponse quotation Failure: " + t.message)
                        binding.quotationProgress.visibility = View.GONE

                    }
                })

        }
        catch (e:Exception){
            e.toString()
            binding.quotationProgress.visibility = View.GONE
        }
    }

    private fun updateCustomerInfo(): AskQuotationModel {
      /*  var qty = binding.qutyentry.text.toString()
        var ftPrice = binding.priceentry.text.toString()
        var totalValues = binding.totalvalue.text.toString()
        var feet = feet_list.get(0).Product_Feet
        var thickness = thickness_list.get(0).Product_Thickness
        var desc_details = binding.descDetails.text.toString()*/


        var askQuotationModel = AskQuotationModel(

           /* customerConversation = desc_details.toString(),
            customerQuantity = qty.toInt(),
            customerSize = feet,
            customerThickness = thickness,
            customerSqftPrice = ftPrice.toDouble(),
            customerPrice = totalValues.toLong(),
            customerProductId = id*/

        )
        return askQuotationModel
    }

    private fun customerDetails(askQuotationModel: AskQuotationModel): JsonObject {

        orderDetails.clear()
        val json = JsonObject()


        var customerobj = JsonObject()
        customerobj.addProperty("customercode", custId)

        var orderTax = JsonObject()
        orderTax.addProperty("taxtype",askQuotationModel.taxtype)
        orderTax.addProperty("taxpercentage",askQuotationModel.taxpercentage)
        orderTax.addProperty("taxamount",askQuotationModel.taxamount)



        var prodId = JsonObject()
      /*  prodId.addProperty("productid", askQuotationModel.customerProductId)*/



        var orderDiscountDetails = JsonObject()
       // orderDiscountDetails.addProperty("discountid",askQuotationModel.discountid)

        //Orderdiscount JSON object
        var orderDiscount = JsonObject()

        orderDiscount.addProperty("discountstatus",askQuotationModel.discountstatus)
        orderDiscount.addProperty("exdiscount",askQuotationModel.exdiscount)




        /*var orderDetails=ArrayList<JsonElement>()*/

        for(productquote in quotationlist) {
            try {
                val orderdetailsObj = JsonObject()
                var discount="1"
                if(productquote.discountId!=""){
                    discount=productquote.discountId
                }


                val quotationModel = AskQuotationModel(

                    customerConversation = productquote.ProductDescription,
                    customerQuantity = productquote.quantity.toInt(),
                    customerSize = productquote.ProductSize,
                    customerThickness = productquote.ProductThickness,
                    customerSqftPrice = productquote.ProductPrice.toDouble(),
                    customerPrice = productquote.totalvalue.toDouble(),
                    customerProductId = productquote.ProductId,
                    discountid = discount

                )
                orderdetailsObj.addProperty(
                    "customerconversation",
                    quotationModel.customerConversation
                )
                orderdetailsObj.addProperty("quantity", quotationModel.customerQuantity)
                orderdetailsObj.addProperty("price", quotationModel.customerPrice)
                orderdetailsObj.addProperty("size", quotationModel.customerSize)
                orderdetailsObj.addProperty("thickness", quotationModel.customerThickness)
                orderdetailsObj.addProperty("sqftprice", quotationModel.customerSqftPrice)
                prodId.addProperty("productid", quotationModel.customerProductId)
                orderDiscountDetails.addProperty("discountid", quotationModel.discountid)
                orderDiscount.add("discount", Gson().toJsonTree(orderDiscountDetails))

                orderdetailsObj.add("product", Gson().toJsonTree(prodId))
                orderdetailsObj.add("orderDiscount", Gson().toJsonTree(orderDiscount))

                orderDetails.add(orderdetailsObj)
            }
            catch (e:Exception){
                e.toString()
            }
        }




        try {
            json.add("customer", Gson().toJsonTree(customerobj))
            json.addProperty("shippinghandling",askQuotationModel.shippinghandling)
            json.addProperty("amountwithtax",askQuotationModel.amountwithtax)
            json.addProperty("amountwithouttax",askQuotationModel.amountwithouttax)
            json.add("orderTax", Gson().toJsonTree(orderTax))
            json.add("orderItems", Gson().toJsonTree(orderDetails))



            Log.d(TAG, "Quotation Json Model " + json.toString())
        } catch (e: Exception) {
        }


        return json
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
        quotationview.visibility=View.GONE
        quotecount.visibility =View.GONE

        quoteitem.isVisible=false
        setupCartCount(cartcount, cart)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search_bar -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.notification_bar -> startActivity(Intent(activity, Notifications::class.java))
            R.id.my_cart -> startActivity(Intent(activity, MyCart::class.java))
        }
        return super.onOptionsItemSelected(item)

    }


    fun fetchQuotationList(){
        try{
            binding.quotationProgress.visibility=View.VISIBLE
            var quotationref=FirebaseDatabase.getInstance().getReference("MyQuotation")
            var query = quotationref.child(custId).orderByChild("quotationStatus").equalTo(true)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    quotationlist.clear()
                    if(snapshot.exists()){

                        for(snap in snapshot.children){
                            var quatationListModel=snap.getValue(QuatationDetailModel::class.java)
                            fetchProducts(quatationListModel?.quotationId)
                        }

                    }
                    else{
                        binding.quotationProgress.visibility=View.GONE
                        binding.quotationNotFoundText.visibility= View.VISIBLE
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    binding.quotationProgress.visibility=View.GONE
                    binding.quotationNotFoundText.visibility= View.VISIBLE
                }
            })



        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun fetchProducts(quotationId: String?) {

        try {
            val prdref = FirebaseDatabase.getInstance().getReference("QuotationProductList")
            quotationId?.let {
                prdref.child(it).child("Products")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            quotationlist.clear()
                            if (snapshot.exists()) {

                                binding.quotationProgress.visibility=View.GONE


                                for (snap in snapshot.children) {

                                    val productdata = snap.getValue(QuatationListModel::class.java)!!
                                    productdata.quotationId=quotationId
                                    quotationlist.add(productdata)

                                    Log.d("--Product list--", productdata!!.ProductBrand)
                                }

                                if(quotationlist.size ==0){
                                    binding.quotationNotFoundText.visibility= View.VISIBLE
                                }
                                else{
                                    binding.quotationNotFoundText.visibility= View.GONE
                                }

                                initializeAdapter()


                            }

                            else {
                                Log.d("No product","true")
                                binding.quotationProgress.visibility=View.GONE
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("Quotation error","true")
                        }
                    })


            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun initializeAdapter() {
        try {
            val quotationListAdapter = QuotationListAdapter(quotationlist, context = this)
            binding.quotationList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.quotationList.setHasFixedSize(true)
            binding.quotationList.adapter = quotationListAdapter
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }


    private fun updateQuotationStatus(){
        try {
            val quotationref = FirebaseDatabase.getInstance().getReference("MyQuotation")
            quotationref.child(custId).child(quotationlist.get(0).quotationId)
                .child("quotationStatus").setValue(false)
            startActivity(Intent(activity, OrderConfirmation::class.java))
            finish()
        }
        catch (e:Exception){
            e.toString()
        }
    }
}